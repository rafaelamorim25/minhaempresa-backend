package br.com.minhaempresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Categoria;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.dto.EmpresaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.exceptions.ObjectNotFoundException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.CategoriaRepository;
import br.com.minhaempresa.repositories.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	SmtpEmailService emailService;
	
	@Autowired
	CategoriaRepository categoriaRepository;

	public Empresa cadastrar(Empresa empresa) {

		empresa.setId(null); 
							
		try {		
			
			Empresa novaEmpresa = empresaRepository.save(empresa);
			
			categoriaRepository.save(new Categoria("Custo", "Gastos relacionados a produção", novaEmpresa));
			categoriaRepository.save(new Categoria("Despesa", "Gastos relacionados ao cotidiano da organização", novaEmpresa));
			
			return novaEmpresa;
			
		} catch (DataIntegrityViolationException e) {
			
			String mensagem = null;
			
			if(e.getMostSpecificCause().getMessage().contains("empresa_cnpj")) {
				mensagem = "Não foi possível criar a conta, o CNPJ já está cadastrado na plataforma";
			} else if(e.getMostSpecificCause().getMessage().contains("empresa_email")) {
				mensagem = "Não foi possível criar a conta, o E-mail já está cadastrado na plataforma";
			} else {
				mensagem = "Não é possível cadastrar a empresa, " + e.getMostSpecificCause().getMessage();
			}
			
			throw new DataIntegrityException(mensagem , e.getCause());
		}
	}
	
	public Empresa buscar() {

		User user = UserService.authenticated();

		return empresaRepository.findById(user.getId()).orElseThrow(() -> new ObjectNotFoundException(
				"Empresa não encontrada, id " + user.getId() + ", Tipo: " + Empresa.class.getName()));
	}

	public Empresa atualizar(Empresa empresa) {
		
		try {		
			
			Empresa novosDados = buscar(); 
			
			atualizarDados(novosDados, empresa);

			return empresaRepository.save(novosDados);
			
		} catch (DataIntegrityViolationException e) {
			
			String mensagem = null;
			
			if(e.getMostSpecificCause().getMessage().contains("empresa_cnpj")) {
				mensagem = "Não foi possível atualizar a conta, o CNPJ já está cadastrado na plataforma";
			} else if(e.getMostSpecificCause().getMessage().contains("empresa_email")) {
				mensagem = "Esse e-mail já é utilizado por outra conta, utilize outro";
			} else {
				mensagem = "Não é possível atualizar a empresa, " + e.getMostSpecificCause().getMessage();
			}
			
			throw new DataIntegrityException(mensagem , e.getCause());
		}
	}

	public void recuperarSenha(String email) {
		
		Empresa empresa = empresaRepository.findByEmail(email);

		if (empresa == null) {
			throw new UsernameNotFoundException(email);
		}
		
		String senha = gerarSenha();		
		empresa.setSenha(passwordEncoder.encode(senha));	
		empresaRepository.save(empresa);
		emailService.sendNewPasswordEmail(empresa.getEmail(), senha);
	}

	public void excluirConta(Integer id) {

		Empresa empresa = buscar();

		try {
			if(empresa.getId().equals(id)) {
				categoriaRepository.deleteByEmpresa(new Empresa(id));
				empresaRepository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ainda não é possível excluir uma conta que contém outros registros",
					e.getCause());
		}
	}

	/* Metodos auxiliares */

	public Empresa fromDTO(EmpresaDTO empresaDTO) {
		return new Empresa(empresaDTO.getCnpj(), empresaDTO.getNomeFantasia(), empresaDTO.getNomeProprietario(),
				empresaDTO.getTelefone(), empresaDTO.getEmail(), passwordEncoder.encode(empresaDTO.getSenha()));
	}

	private void atualizarDados(Empresa novosDados, Empresa empresa) {
		novosDados.setCnpj(empresa.getCnpj());
		novosDados.setEmail(empresa.getEmail());
		novosDados.setNomeFantasia(empresa.getNomeFantasia());
		novosDados.setNomeProprietario(empresa.getNomeProprietario());
		novosDados.setTelefone(empresa.getTelefone());
		novosDados.setSenha(empresa.getSenha());
	}
	
	private static String gerarSenha(){
		int qtdeMaximaCaracteres = 8;
	    String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8",
	                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
	                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
	                "x", "y", "z"};
	    
		StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();     
	}
}
