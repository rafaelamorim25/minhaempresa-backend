package br.com.minhaempresa.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.dto.ClienteDTO;
import br.com.minhaempresa.services.ClienteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody ClienteDTO clienteDTO){
		
		Cliente c = clienteService.cadastrar(clienteDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listar(){
		
		
		List<ClienteDTO> list = clienteService.listar();
		
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Integer id){
		
		
		ClienteDTO cliente = clienteService.buscar(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Cliente> atualizar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		

		Cliente c = clienteService.atualizar(clienteDTO, id);
		
		return ResponseEntity.ok().body(c);
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id){
		
		clienteService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}


}
