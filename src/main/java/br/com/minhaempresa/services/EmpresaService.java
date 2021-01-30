package br.com.minhaempresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.dto.EmpresaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.exceptions.ObjectNotFoundException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	SmtpEmailService emailService;

	public Empresa cadastrar(Empresa empresa) {

		empresa.setId(null); // Serve para garantir que é uma nova empresa, pois o metodo save serve para
								// inserir e atualizar

		try {
			return empresaRepository.save(empresa);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível cadastrar a empresa, já existe uma conta com esse email ou cnpj", e.getCause());
		}
	}
	
	public Empresa buscar() {

		User user = UserService.authenticated();

		return empresaRepository.findById(user.getId()).orElseThrow(() -> new ObjectNotFoundException(
				"Empresa não encontrada, id " + user.getId() + ", Tipo: " + Empresa.class.getName()));
	}

	public Empresa atualizar(Empresa empresa) {

		Empresa novosDados = buscar(); // Verifica se a empresa que será atualizada existe

		atualizarDados(novosDados, empresa);

		return empresaRepository.save(novosDados);
	}

	public void recuperarSenha(String email) {
		
		Empresa empresa = empresaRepository.findByEmail(email);

		if (empresa == null) {
			throw new UsernameNotFoundException(email);
		}
		
		String senha = gerarSenha();
		
		empresa.setSenha(passwordEncoder.encode(senha));
		
		empresaRepository.save(empresa);
		//atualizar o cliente com a nova senha
		
		emailService.sendNewPasswordEmail(empresa, senha);
		//enviar a nova senha para o email da empresa

	}

	public void excluirConta(Integer id) {

		buscar();

		try {
			empresaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ainda não é possível excluir uma conta que contém outros registros",
					e.getCause());
		}

	}
	
	public Boolean senhaIsvalida(String senha) {
		
		User user = UserService.authenticated();
		
		System.out.println("Senha guardada no banco: " + user.getPassword());
		System.out.println("Senha da requisicao: " + passwordEncoder.encode(senha));

		if(user.getPassword().equals(passwordEncoder.encode(senha))) {
			
			return true;
		}
		
		return false;
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

	/* fim dos metodos auxiliares */
}
