package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.dto.RecebimentoDTO;
import br.com.minhaempresa.dto.VendaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	public Cliente cadastrar(ClienteDTO clienteDTO) {

		User user = UserService.authenticated();

		if (cpfValido(clienteDTO.getCpf(), user.getId())) {

			Cliente cliente = Cliente.builder().cpf(clienteDTO.getCpf()).email(clienteDTO.getEmail())
					.nome(clienteDTO.getNome()).telefone(clienteDTO.getTelefone()).empresa(new Empresa(user.getId()))
					.build();

			return repository.save(cliente);
		}

		throw new DataIntegrityException("Não foi possível adicionar o cliente, erro: Esse CPF já está vinculado a outro cliente");
	}

	boolean cpfValido(String cpf, Integer empresaId) {

		List<Cliente> clientes = repository.findByEmpresa(new Empresa(empresaId));

		for (Cliente c : clientes) {
			if (c.getCpf().equalsIgnoreCase(cpf)) {
				return false;
			}
		}

		return true;
	}

	public List<ClienteDTO> listar() {
		User user = UserService.authenticated();

		List<Cliente> clientes = repository.findByEmpresa(new Empresa(user.getId()));

		List<ClienteDTO> list = new ArrayList<ClienteDTO>();

		for (Cliente cliente : clientes) {

			Set<RecebimentoDTO> recebimentos = new HashSet<>();

			for (Recebimento recebimento : cliente.getRecebimentos()) {

				recebimentos.add(RecebimentoDTO.builder().id(recebimento.getId()).valor(recebimento.getValor())
						.data(recebimento.getData()).estornada(recebimento.getEstornada()).build());
			}

			list.add(ClienteDTO.builder().id(cliente.getId()).cpf(cliente.getCpf()).email(cliente.getEmail())
					.nome(cliente.getNome()).telefone(cliente.getTelefone()).recebimentos(recebimentos)
					.saldo(cliente.getSaldo()).build());
		}

		return list;
	}

	public ClienteDTO buscar(Integer id) {
		
		User user = UserService.authenticated();
		
		if(idValido(id, user.getId())) {
			Cliente cliente = repository.findById(id).get();

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

			return ClienteDTO.builder().id(cliente.getId()).cpf(cliente.getCpf()).email(cliente.getEmail())
					.nome(cliente.getNome()).telefone(cliente.getTelefone()).recebimentos(recebimentos).vendas(vendas)
					.saldo(cliente.getSaldo()).build();
		}
		throw new DataIntegrityException("Não foi possível buscar esse cliente");
	}
	
	boolean idValido(Integer id, Integer empresaId) {
		List<Cliente> clientes = repository.findByEmpresa(new Empresa(empresaId));

		for (Cliente c : clientes) {
			if (c.getId().equals(id)) {
				return true;
			}
		}

		return false;
	}

	public Cliente atualizar(ClienteDTO clienteDTO, Integer id) {

		User user = UserService.authenticated();

		Cliente c = repository.findById(id).get();

		if (clienteDTO.getId() == id && c.getEmpresa().getId() == user.getId()) {

			Cliente cliente = Cliente.builder().cpf(clienteDTO.getCpf()).email(clienteDTO.getEmail())
					.nome(clienteDTO.getNome()).telefone(clienteDTO.getTelefone()).empresa(new Empresa(user.getId()))
					.build();

			return repository.save(cliente);

		}

		throw new DataIntegrityException("Impossível atualizar esse cliente");
	}

	public void excluir(Integer id) {

		User user = UserService.authenticated();

		Cliente cliente = repository.findById(id).get();

		if (cliente.getEmpresa().getId() == user.getId() && cliente.getSaldo().equals(0F)) {

			repository.deleteById(id);
		}
		
		throw new DataIntegrityException("Não foi possível excluir o cliente, pois ele possiu dívida");
	}
}
