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

import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.VendaDTO;
import br.com.minhaempresa.services.VendaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/vendas")
public class VendaResource {
	
	@Autowired
	private VendaService vendaService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody VendaDTO vendaDTO){
		
		Venda v = vendaService.cadastrar(vendaDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(v.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VendaDTO>> listar(){
		
		
		List<VendaDTO> list = vendaService.listar();
		
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> estornar(@PathVariable Integer id){
		
		vendaService.estornar(id);
		
		return ResponseEntity.noContent().build();
	}

}
