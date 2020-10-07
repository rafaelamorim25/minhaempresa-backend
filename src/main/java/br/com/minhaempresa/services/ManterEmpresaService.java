package br.com.minhaempresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.exceptions.ObjectNotFoundException;
import br.com.minhaempresa.repositories.EmpresaRepository;

@Service
public class ManterEmpresaService {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public Empresa buscar(Integer id) {
		return empresaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada, id " + id + ", Tipo: " + Empresa.class.getName()));
	}

}
