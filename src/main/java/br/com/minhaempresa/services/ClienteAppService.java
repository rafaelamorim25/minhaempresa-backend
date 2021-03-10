package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.ClienteApp;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.ClienteAppDTO;
import br.com.minhaempresa.dto.CompraDTO;
import br.com.minhaempresa.dto.RecebimentoDTO;
import br.com.minhaempresa.dto.VendaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.exceptions.ObjectNotFoundException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteAppRepository;
import br.com.minhaempresa.repositories.ClienteRepository;

@Service
public class ClienteAppService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	ClienteAppRepository clienteAppRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	SmtpEmailService emailService;

	public ClienteApp cadastrar(ClienteApp clienteApp) {

		clienteApp.setId(null);

		try {

			ClienteApp cliente = clienteAppRepository.save(clienteApp);

			return cliente;

		} catch (DataIntegrityViolationException e) {

			String mensagem = null;

			if (e.getMostSpecificCause().getMessage().contains("cliente_app_cpf")) {
				mensagem = "Não foi possível criar a conta, o CPF já está cadastrado na plataforma";
			} else if (e.getMostSpecificCause().getMessage().contains("cliente_app_email")) {
				mensagem = "Não foi possível criar a conta, o E-mail já está cadastrado na plataforma";
			} else {
				mensagem = "Não é possível cadastrar a conta, " + e.getMostSpecificCause().getMessage();
			}

			throw new DataIntegrityException(mensagem, e.getCause());
		}
	}

	public ClienteApp buscar() {
		User user = UserService.authenticated();

		return clienteAppRepository.findById(user.getId()).orElseThrow(() -> new ObjectNotFoundException(
				"Cliete não encontrado, id " + user.getId() + ", Tipo: " + Empresa.class.getName()));
	}

	public ClienteApp atualizar(ClienteApp clienteApp) {

		try {
			
			ClienteApp novosDados = buscar();
			atualizarDados(novosDados, clienteApp);
			
			return clienteAppRepository.save(novosDados);
			
		} catch (DataIntegrityViolationException e) {

			String mensagem = null;

			if (e.getMostSpecificCause().getMessage().contains("empresa_cnpj")) {
				mensagem = "Não foi possível atualizar a conta, o CPF já está cadastrado na plataforma";
			} else if (e.getMostSpecificCause().getMessage().contains("empresa_email")) {
				mensagem = "Esse e-mail já é utilizado por outra conta, utilize outro";
			} else {
				mensagem = "Não é possível atualizar o cliente, " + e.getMostSpecificCause().getMessage();
			}

			throw new DataIntegrityException(mensagem, e.getCause());
		}
	}

	public void recuperarSenha(String email) {
		ClienteApp clienteApp = clienteAppRepository.findByEmail(email);

		if (clienteApp == null) {
			throw new UsernameNotFoundException(email);
		}

		String senha = gerarSenha();
		clienteApp.setSenha(passwordEncoder.encode(senha));
		clienteAppRepository.save(clienteApp);
		emailService.sendNewPasswordEmail(clienteApp.getEmail(), senha);
	}

	public void excluirConta(Integer id) {

		ClienteApp cliente = buscar();

		try {
			if(cliente.getId().equals(id)) {
				clienteAppRepository.deleteById(cliente.getId());
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir a conta",
					e.getCause());
		}
	}

	public ClienteApp fromDTO(ClienteAppDTO clienteAppDTO) {

		return new ClienteApp(clienteAppDTO.getNome(), clienteAppDTO.getCpf(), clienteAppDTO.getTelefone(),
				clienteAppDTO.getEmail(), passwordEncoder.encode(clienteAppDTO.getSenha()));

	}

	private void atualizarDados(ClienteApp novosDados, ClienteApp clienteApp) {

		novosDados.setEmail(clienteApp.getEmail());
		novosDados.setNome(clienteApp.getNome());
		novosDados.setTelefone(clienteApp.getTelefone());
		novosDados.setSenha(clienteApp.getSenha());
	}

	private static String gerarSenha() {
		int qtdeMaximaCaracteres = 8;
		String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return senha.toString();
	}

	// Buscar Comprar

	public List<CompraDTO> buscarCompras() {

		ClienteApp c = buscar();

		List<CompraDTO> compras = new ArrayList<>();

		List<Cliente> clientes = clienteRepository.findAll();

		for (Cliente cliente : clientes) {

			if (c.getCpf().equals(cliente.getCpf())) {

				Set<RecebimentoDTO> recebimentos = new HashSet<>();

				for (Recebimento recebimento : cliente.getRecebimentos()) {

					recebimentos.add(RecebimentoDTO.builder().id(recebimento.getId()).valor(recebimento.getValor())
							.data(recebimento.getData()).estornada(recebimento.getEstornada()).build());
				}

				Set<VendaDTO> vendas = new HashSet<>();

				for (Venda venda : cliente.getVendas()) {

					vendas.add(VendaDTO.builder().id(venda.getId()).valor(venda.getValor()).data(venda.getData())
							.estornada(venda.getEstornada()).formaPagamento(venda.getFormaPagamento())
							.descricao(venda.getDescricao()).build());
				}

				compras.add(CompraDTO.builder().nomeFantasia(cliente.getEmpresa().getNomeFantasia())
						.cnpj(cliente.getEmpresa().getCnpj()).telefone(cliente.getEmpresa().getTelefone())
						.divida(cliente.getSaldo()).compras(vendas).pagamentos(recebimentos)
						.visualizar(cliente.getVisualizar()).clienteId(cliente.getId()).build());
			}
		}
		return compras;
	}

	public void visualizarEmpresa(Integer clienteId) {

		ClienteApp c = buscar();

		Cliente cliente = clienteRepository.findById(clienteId).get();

		if (cliente.getCpf().equals(c.getCpf())) {

			cliente.trocarStatusVisualizacao();
			clienteRepository.save(cliente);

		}
	}
}
