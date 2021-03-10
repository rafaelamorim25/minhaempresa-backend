package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.dto.VendaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteRepository;
import br.com.minhaempresa.repositories.VendaRepository;

@Service
public class VendaService {

	@Autowired
	VendaRepository repository;

	@Autowired
	ClienteRepository clienteRepository;

	public Venda cadastrar(VendaDTO vendaDTO) {

		try {
		
		User user = UserService.authenticated();
		
		Cliente cliente = clienteRepository.findById(vendaDTO.getCliente().getId()).get();
		
		if(cliente.getEmpresa().getId().equals(user.getId())) {
			Venda venda = Venda.builder().valor(vendaDTO.getValor()).data(vendaDTO.getData())
					.descricao(vendaDTO.getDescricao()).cliente(new Cliente(vendaDTO.getCliente().getId()))
					.formaPagamento(vendaDTO.getFormaPagamento()).build();

			return repository.save(venda);
		}
		}catch(Exception e) {
			throw new DataIntegrityException(e.getMessage(), e.getCause());
		}
		throw new DataIntegrityException("Não foi possível adicionar a venda");
	}

	public List<VendaDTO> listar() {
		User user = UserService.authenticated();

		List<Cliente> clientes = clienteRepository.findByEmpresa(new Empresa(user.getId()));
		List<VendaDTO> list = new ArrayList<VendaDTO>();

		for (Cliente cliente : clientes) {
			for (Venda venda : cliente.getVendas()) {

				list.add(VendaDTO.builder().id(venda.getId()).valor(venda.getValor()).data(venda.getData())
						.descricao(venda.getDescricao())
						.cliente(ClienteDTO.builder().id(cliente.getId()).cpf(cliente.getCpf()).nome(cliente.getNome())
								.email(cliente.getEmail()).telefone(cliente.getTelefone()).build())
						.formaPagamento(venda.getFormaPagamento()).estornada(venda.getEstornada()).build());
			}
		}
		
		return list;
	}

	public void estornar(Integer id) {

		User user = UserService.authenticated();
		Venda v = repository.findById(id).get();

		if (v.getCliente().getEmpresa().getId() == user.getId() && v.getCliente().getSaldo() >= v.getValor()) {
			v.estornar();
			repository.save(v);
		} else {
			throw new DataIntegrityException("Não é possível estornar uma venda de modo que a dívida do cliente fique negativa");
		}
	}
}
