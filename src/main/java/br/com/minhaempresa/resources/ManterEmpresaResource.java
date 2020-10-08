package br.com.minhaempresa.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.services.ManterEmpresaService;

@RestController
@RequestMapping(value="/minhaempresa")
public class ManterEmpresaResource {
	
	@Autowired
	private ManterEmpresaService manterEmpresaService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> buscar(@PathVariable Integer id) {
		
		Empresa empresa = manterEmpresaService.buscar(id);
		
		return ResponseEntity.ok().body(empresa);
	}
	
	@RequestMapping(value = "/faça-parte", method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@RequestBody Empresa empresa){
		
		empresa = manterEmpresaService.cadastrar(empresa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Empresa empresa, @PathVariable Integer id){
		
		empresa.setId(id);
		
		empresa = manterEmpresaService.atualizar(empresa);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirConta(@PathVariable Integer id){
		
		manterEmpresaService.excluirConta(id);
		
		return ResponseEntity.noContent().build();
	}
}
