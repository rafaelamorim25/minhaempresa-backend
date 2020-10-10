package br.com.minhaempresa.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.dto.ConfirmacaoSenhaDTO;
import br.com.minhaempresa.dto.EmailDTO;
import br.com.minhaempresa.dto.EmpresaDTO;
import br.com.minhaempresa.dto.SenhaDTO;
import br.com.minhaempresa.services.ManterEmpresaService;

@RestController
@RequestMapping(value="/minhaempresa")
public class ManterEmpresaResource {
	
	@Autowired
	private ManterEmpresaService manterEmpresaService;
	
	@RequestMapping(value = "/faça-parte", method = RequestMethod.POST)
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody EmpresaDTO empresaDTO){
		
		Empresa empresa = manterEmpresaService.fromDTO(empresaDTO);
		
		empresa = manterEmpresaService.cadastrar(empresa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> buscar(@PathVariable Integer id) {
		
		Empresa empresa = manterEmpresaService.buscar(id);
		
		return ResponseEntity.ok().body(empresa);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody EmpresaDTO empresaDTO, @PathVariable Integer id){
		Empresa empresa = manterEmpresaService.fromDTO(empresaDTO);
		empresa.setId(id);
		
		empresa = manterEmpresaService.atualizar(empresa);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/recuperar-senha", method = RequestMethod.POST)
	public ResponseEntity<Void> recuperarSenha(@Valid @RequestBody EmailDTO emailDTO){
		
		manterEmpresaService.recuperarSenha(emailDTO.getEmail());
		
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/verificar-senha", method = RequestMethod.POST)
	public ResponseEntity<ConfirmacaoSenhaDTO> verificarSenha(@Valid @RequestBody SenhaDTO senhaDTO){
		
		ConfirmacaoSenhaDTO confirmacao = new ConfirmacaoSenhaDTO();
		confirmacao.setSenhavalida(manterEmpresaService.senhaIsvalida(senhaDTO.getSenha()));
		
		return ResponseEntity.ok().body(confirmacao);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirConta(@PathVariable Integer id){
		
		manterEmpresaService.excluirConta(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
