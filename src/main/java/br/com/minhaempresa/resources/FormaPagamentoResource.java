package br.com.minhaempresa.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.minhaempresa.domain.FormaPagamento;
import br.com.minhaempresa.services.FormaPagamentoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/formas-pagamento")
public class FormaPagamentoResource {
	
	@Autowired
	FormaPagamentoService formaPagamentoService;
	
	@PreAuthorize("hasAnyRole('EMPRESA')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FormaPagamento>> listar(){
		
		List<FormaPagamento> list = formaPagamentoService.listar();
		
		return ResponseEntity.ok().body(list);
	}
}
