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

import br.com.minhaempresa.domain.Saida;
import br.com.minhaempresa.dto.SaidaDTO;
import br.com.minhaempresa.services.SaidaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/saidas")
public class SaidaResource {
	
	@Autowired
	private SaidaService saidaService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody SaidaDTO saidaDTO){
		
		Saida s = saidaService.cadastrar(saidaDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(s.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SaidaDTO>> listar(){
		
		
		List<SaidaDTO> list = saidaService.listar();
		
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Saida> atualizar(@Valid @RequestBody SaidaDTO saidaDTO, @PathVariable Integer id){
		
		System.out.println("Mandou atualizar");
		
		//Categoria categoria = manterCategoriaService.fromDTO(categoriaDTO);

		Saida s = saidaService.atualizar(saidaDTO, id);
		
		return ResponseEntity.ok().body(s);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id){
		
		saidaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
}
