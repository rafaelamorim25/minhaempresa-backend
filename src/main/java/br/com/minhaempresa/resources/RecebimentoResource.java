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

import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.dto.RecebimentoDTO;
import br.com.minhaempresa.services.RecebimentoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/recebimentos")
public class RecebimentoResource {
	
	@Autowired
	private RecebimentoService recebimentoService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody RecebimentoDTO recebimentoDTO){
		
		Recebimento r = recebimentoService.cadastrar(recebimentoDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<RecebimentoDTO>> listar(){
		
		
		List<RecebimentoDTO> list = recebimentoService.listar();
		
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> estornar(@PathVariable Integer id){
		
		recebimentoService.estornar(id);
		
		return ResponseEntity.noContent().build();
	}

}
