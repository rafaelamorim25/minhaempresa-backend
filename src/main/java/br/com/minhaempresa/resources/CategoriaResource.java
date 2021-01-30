package br.com.minhaempresa.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.minhaempresa.domain.Categoria;
import br.com.minhaempresa.dto.CategoriaDTO;
import br.com.minhaempresa.services.CategoriaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService manterCategoriaService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody CategoriaDTO categoriaDTO){
		
		//Categoria categoria = manterCategoriaService.fromDTO(categoriaDTO);
		
		Categoria categoria = manterCategoriaService.cadastrar(categoriaDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listar(){
		
		
		List<CategoriaDTO> list = manterCategoriaService.listar();
		
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id){

		Categoria categoria = manterCategoriaService.atualizar(categoriaDTO, id);
		
		return ResponseEntity.ok().body(categoria);
	}
	

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id){
		
		manterCategoriaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}

}
