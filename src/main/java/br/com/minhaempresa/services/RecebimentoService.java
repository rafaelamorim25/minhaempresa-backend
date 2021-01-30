package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.dto.RecebimentoDTO;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.RecebimentoRepository;

@Service
public class RecebimentoService {
	
	@Autowired
	RecebimentoRepository repository;
	
	public Recebimento  cadastrar(RecebimentoDTO vendaDTO) {
		
		//User user = UserService.authenticated();
		
	
		Recebimento recebimento = Recebimento.builder()
				.valor(vendaDTO.getValor())
				.data(vendaDTO.getData())
				.cliente(new Cliente(vendaDTO.getCliente().getId()))
				.build();
	
		
		return repository.save(recebimento);	
	}
	
	 public List<RecebimentoDTO> listar(){
		//User user = UserService.authenticated();
		
		List<Recebimento> recebimentos = repository.findAll();
		
		List<RecebimentoDTO> list = new ArrayList<RecebimentoDTO>();
		
		for (Recebimento recebimento : recebimentos) {
			
			list.add(
					RecebimentoDTO.builder()
					.id(recebimento.getId())
					.valor(recebimento.getValor())
					.data(recebimento.getData())
					.estornada(recebimento.getEstornada())
					.cliente(ClienteDTO.builder().id(recebimento.getCliente().getId())
							.cpf(recebimento.getCliente().getCpf())
							.nome(recebimento.getCliente().getNome())
							.email(recebimento.getCliente().getEmail())
							.telefone(recebimento.getCliente().getTelefone()).build())
					.build()
					);
		}

		return list;
	}
	
	public void estornar(Integer id) {
		
		User user = UserService.authenticated();
		
		Recebimento r = repository.findById(id).get();
		
		if(r.getCliente().getEmpresa().getId() == user.getId()) {
			
			r.estornar();
			
			repository.save(r);
			
			
			System.out.println("Estornou");
			
		}else {
			
			System.out.println("Não estornou");
			
		}

	}

}
