package br.com.minhaempresa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Categoria;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.dto.CategoriaDTO;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;

	public Categoria cadastrar(CategoriaDTO categoriaDTO) {

		User user = UserService.authenticated();

		Categoria categoria = new Categoria(categoriaDTO.getNome(), categoriaDTO.getDescricao(),
				new Empresa(user.getId()));

		return repository.save(categoria);

	}

	public List<CategoriaDTO> listar() {
		User user = UserService.authenticated();

		List<Categoria> categorias = repository.findByEmpresa(new Empresa(user.getId()));

		List<CategoriaDTO> list = new ArrayList<CategoriaDTO>();

		for (Categoria x : categorias) {

			list.add(new CategoriaDTO(x.getId(), x.getNome(), x.getDescricao()));
		}

		return list;
	}

	public Categoria atualizar(CategoriaDTO categoriaDTO, Integer id) {

		User user = UserService.authenticated();

		Categoria c = repository.findById(id).get();

		if (c.getEmpresa().getId() == user.getId()) {

			c.setDescricao(categoriaDTO.getDescricao());
			c.setNome(categoriaDTO.getNome());

			return repository.save(c);

		}

		throw new DataIntegrityException("Impossível atualizar essa categoria");
	}

	public void excluir(Integer id) {
		User user = UserService.authenticated();
		Categoria categoria = repository.findById(id).get();
		
		if (categoria.getEmpresa().getId() == user.getId() && categoria.getSaidas().isEmpty()) {
			repository.deleteById(id);
		}
		throw new DataIntegrityException("Impossível excluir uma categoria que contém saídas");
	}
}
