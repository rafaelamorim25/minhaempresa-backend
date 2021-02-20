package br.com.minhaempresa.resources;

import java.net.URI;

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

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.dto.EmailDTO;
import br.com.minhaempresa.dto.EmpresaDTO;
import br.com.minhaempresa.services.EmpresaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/minhaempresa")
public class EmpresaResource {
	
	@Autowired
	private EmpresaService manterEmpresaService;
	
	@RequestMapping(value = "/faca-parte", method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody EmpresaDTO empresaDTO){
		
		Empresa empresa = manterEmpresaService.fromDTO(empresaDTO);
		
		empresa = manterEmpresaService.cadastrar(empresa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/minhaconta", method = RequestMethod.GET)
	public ResponseEntity<Empresa> buscar() {
		
		Empresa empresa = manterEmpresaService.buscar();
		
		return ResponseEntity.ok().body(empresa);
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Empresa> atualizar(@Valid @RequestBody EmpresaDTO empresaDTO){
		
		System.out.println("Mandou atualizar");
		
		Empresa empresa = manterEmpresaService.fromDTO(empresaDTO);

		empresa = manterEmpresaService.atualizar(empresa);
		
		return ResponseEntity.ok().body(empresa);
	}
	
	@RequestMapping(value = "/recuperar-senha", method = RequestMethod.POST)
	public ResponseEntity<Void> recuperarSenha(@Valid @RequestBody EmailDTO emailDTO){
		
		manterEmpresaService.recuperarSenha(emailDTO.getEmail());
		
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirConta(@PathVariable Integer id){
		
		manterEmpresaService.excluirConta(id);
		
		return ResponseEntity.noContent().build();
	}
}
