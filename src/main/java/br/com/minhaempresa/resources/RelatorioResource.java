package br.com.minhaempresa.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.minhaempresa.dto.PeriodoDTO;
import br.com.minhaempresa.dto.RelatorioDTO;
import br.com.minhaempresa.services.RelatorioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/relatorio")
public class RelatorioResource {
	
	@Autowired
	private RelatorioService relatorioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<RelatorioDTO> exibir(@Valid @RequestBody PeriodoDTO periodo){
		
		RelatorioDTO relatorio = relatorioService.exibir(periodo);
		
		return ResponseEntity.ok().body(relatorio);
	}

}
