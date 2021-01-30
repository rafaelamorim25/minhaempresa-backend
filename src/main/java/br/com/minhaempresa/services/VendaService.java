package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.dto.VendaDTO;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	VendaRepository repository;
	
	public Venda  cadastrar(VendaDTO vendaDTO) {
		
		//User user = UserService.authenticated();
		
	
		Venda venda = Venda.builder()
				.valor(vendaDTO.getValor())
				.data(vendaDTO.getData())
				.descricao(vendaDTO.getDescricao())
				.cliente(new Cliente(vendaDTO.getCliente().getId()))
				.formaPagamento(vendaDTO.getFormaPagamento())
				.build();
	
		
		return repository.save(venda);	
	}
	
	 public List<VendaDTO> listar(){
		//User user = UserService.authenticated();
		
		List<Venda> vendas = repository.findAll();
		
		List<VendaDTO> list = new ArrayList<VendaDTO>();
		
		for (Venda venda : vendas) {
			
			list.add(
					VendaDTO.builder()
					.id(venda.getId())
					.valor(venda.getValor())
					.data(venda.getData())
					.descricao(venda.getDescricao())
					.cliente(ClienteDTO.builder().id(venda.getCliente().getId())
							.cpf(venda.getCliente().getCpf())
							.nome(venda.getCliente().getNome())
							.email(venda.getCliente().getEmail())
							.telefone(venda.getCliente().getTelefone()).build())
					.formaPagamento(venda.getFormaPagamento())
					.estornada(venda.getEstornada())
					.build()
					);
		}

		return list;
	}
	
	public void estornar(Integer id) {
		
		User user = UserService.authenticated();
		
		Venda v = repository.findById(id).get();
		
		if(v.getCliente().getEmpresa().getId() == user.getId()) {
			
			v.estornar();
			
			repository.save(v);
			
			
			System.out.println("Estornou");
			
		}else {
			
			System.out.println("Não estornou");
			
		}

	}

}
