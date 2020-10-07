package br.com.minhaempresa.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.services.ManterEmpresaService;

@RestController
@RequestMapping(value="/minhaempresa")
public class ManterEmpresaResource {
	
	@Autowired
	private ManterEmpresaService manterEmpresaService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		
		Empresa empresa = manterEmpresaService.buscar(id);
		
		return ResponseEntity.ok().body(empresa);
	}

}
