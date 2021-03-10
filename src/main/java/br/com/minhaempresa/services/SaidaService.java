package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Categoria;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Saida;
import br.com.minhaempresa.dto.CategoriaDTO;
import br.com.minhaempresa.dto.SaidaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.SaidaRepository;

@Service
public class SaidaService{
	
	@Autowired
	SaidaRepository repository;
	
	public Saida  cadastrar(SaidaDTO saidaDTO) {
		
		User user = UserService.authenticated();
		
		Saida saida = Saida.builder().valor(saidaDTO.getValor()).descricao(saidaDTO.getDescricao()).data(saidaDTO.getData()).categoria(new Categoria(saidaDTO.getCategoria().getId())).empresa(new Empresa(user.getId())).build();
		
		return repository.save(saida);	
	}
	
	public List<SaidaDTO> listar(){
		User user = UserService.authenticated();
		
		List<Saida> saidas = repository.findByEmpresa(new Empresa(user.getId()));
		
		List<SaidaDTO> list = new ArrayList<SaidaDTO>();
		
		for (Saida saida : saidas) {
			
			list.add(SaidaDTO.builder()
					.id(saida.getId())
					.descricao(saida.getDescricao())
					.valor(saida.getValor())
					.data(saida.getData())
					.categoria(
						CategoriaDTO.builder()
						.id(saida.getCategoria().getId())
						.nome(saida.getCategoria().getNome())
						.descricao(saida.getCategoria().getDescricao())
						.build()
							)
					.build());
		}

		return list;
	}
	
	
	public Saida atualizar(SaidaDTO saidaDTO, Integer id) {
		
		User user = UserService.authenticated();
		
		Saida s = repository.findById(id).get();
		
		if(saidaDTO.getId() == id && s.getEmpresa().getId() == user.getId()) {
			
			Saida saida = Saida.builder()
					.descricao(saidaDTO.getDescricao())
					.data(saidaDTO.getData())
					.categoria(new Categoria(saidaDTO.getCategoria().getId()))
					.empresa(new Empresa(user.getId()))
					.id(id)
					.valor(saidaDTO.getValor())
					.build();

			return repository.save(saida);
		}

		throw new DataIntegrityException("Impossível atualizar essa saida");
	}
	
	public void excluir(Integer id) {
		
		User user = UserService.authenticated();
		
		Optional<Saida> s = repository.findById(id);
		
		if(s.get().getEmpresa().getId() == user.getId()) {
			repository.deleteById(id);		
		}else {
			throw new DataIntegrityException("Impossível excluir essa saida");
		}
	}
}
