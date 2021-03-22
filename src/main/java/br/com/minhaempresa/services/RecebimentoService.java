package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.dto.RecebimentoDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteRepository;
import br.com.minhaempresa.repositories.RecebimentoRepository;

@Service
public class RecebimentoService {

	@Autowired
	RecebimentoRepository repository;

	@Autowired
	ClienteRepository clienteRepository;

	public Recebimento cadastrar(RecebimentoDTO recebimentoDTO) {

		try {
			User user = UserService.authenticated();
			Cliente cliente = clienteRepository.findById(recebimentoDTO.getCliente().getId()).get();

			if (cliente.getEmpresa().getId().equals(user.getId()) && cliente.getSaldo() >= recebimentoDTO.getValor()) {
				Recebimento recebimento = Recebimento.builder().valor(recebimentoDTO.getValor())
						.data(recebimentoDTO.getData()).cliente(new Cliente(recebimentoDTO.getCliente().getId()))
						.build();

				return repository.save(recebimento);
			}
		} catch (Exception e) {
			throw new DataIntegrityException(e.getMessage(), e.getCause());
		}
		throw new DataIntegrityException("Não foi possível adicionar o recebimento");
	}

	public List<RecebimentoDTO> listar() {
		User user = UserService.authenticated();

		List<Cliente> clientes = clienteRepository.findByEmpresa(new Empresa(user.getId()));
		List<RecebimentoDTO> list = new ArrayList<RecebimentoDTO>();

		for (Cliente cliente : clientes) {
			for (Recebimento recebimento : cliente.getRecebimentos()) {
				list.add(RecebimentoDTO.builder().id(recebimento.getId()).valor(recebimento.getValor())
						.data(recebimento.getData()).estornada(recebimento.getEstornada())
						.cliente(ClienteDTO.builder().id(recebimento.getCliente().getId())
								.cpf(recebimento.getCliente().getCpf()).nome(recebimento.getCliente().getNome())
								.email(recebimento.getCliente().getEmail())
								.telefone(recebimento.getCliente().getTelefone()).build())
						.build());
			}
		}

		return list;
	}

	public void estornar(Integer id) {

		User user = UserService.authenticated();
		Recebimento r = repository.findById(id).get();

		if (r.getCliente().getEmpresa().getId() == user.getId()) {
			r.estornar();
			repository.save(r);
		} else {
			throw new DataIntegrityException("Não foi possível estornar o recebimento");
		}
	}
}
