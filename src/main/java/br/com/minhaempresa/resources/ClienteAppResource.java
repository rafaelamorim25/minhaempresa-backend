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

import br.com.minhaempresa.domain.ClienteApp;
import br.com.minhaempresa.dto.ClienteAppDTO;
import br.com.minhaempresa.dto.CompraDTO;
import br.com.minhaempresa.dto.EmailDTO;
import br.com.minhaempresa.services.ClienteAppService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/cliente")
public class ClienteAppResource {
	
	@Autowired
	private ClienteAppService clienteAppService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody ClienteAppDTO clienteAppDTO){
		
		ClienteApp clienteApp = clienteAppService.fromDTO(clienteAppDTO);
		
		clienteApp = clienteAppService.cadastrar(clienteApp);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteApp.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@RequestMapping(value = "/minhaconta", method = RequestMethod.GET)
	public ResponseEntity<ClienteApp> buscar() {
		
		ClienteApp clienteApp = clienteAppService.buscar();
		
		return ResponseEntity.ok().body(clienteApp);
	}
	
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@RequestMapping(value = "/compras", method = RequestMethod.GET)
	public ResponseEntity<List<CompraDTO>> buscarCompras() {
		
		List<CompraDTO> compras = clienteAppService.buscarCompras();
		
		return ResponseEntity.ok().body(compras);
	}
	
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<ClienteApp> atualizar(@Valid @RequestBody ClienteAppDTO clienteAppDTO){
		
		ClienteApp clienteApp = clienteAppService.fromDTO(clienteAppDTO);

		clienteApp = clienteAppService.atualizar(clienteApp);
		
		return ResponseEntity.ok().body(clienteApp);
	}
	
	@RequestMapping(value = "/recuperar-senha", method = RequestMethod.POST)
	public ResponseEntity<Void> recuperarSenha(@Valid @RequestBody EmailDTO emailDTO){
		
		clienteAppService.recuperarSenha(emailDTO.getEmail());
		
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> trocarStatusVisualizacao(@PathVariable Integer id){
		
		clienteAppService.visualizarEmpresa(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirConta(@PathVariable Integer id){
		
		clienteAppService.excluirConta(id);
		
		return ResponseEntity.noContent().build();
	}
}
