package br.com.minhaempresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.exceptions.DataIntegrityException;
import br.com.minhaempresa.exceptions.ObjectNotFoundException;
import br.com.minhaempresa.repositories.EmpresaRepository;

@Service
public class ManterEmpresaService {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public Empresa buscar(Integer id) {
		
		//Apenas a empresa pode visualizar suas informações
		
		return empresaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada, id " + id + ", Tipo: " + Empresa.class.getName()));
	}
	
	public Empresa cadastrar(Empresa empresa) {
		
		//Qualquer pessoa pode se registrar na plataforma
		
		empresa.setId(null); //Serve para garantir que é uma nova empresa, pois o metodo save serve para inserir e atualizar
		
		try {
		return empresaRepository.save(empresa);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível cadastrar a empresa, já existe uma conta com esse email ou cnpj", e.getCause());
		}
	}
	
	public Empresa atualizar(Empresa empresa) {
		
		//Apenas a empresa pode atualizar suas informações
		
		buscar(empresa.getId()); //Verifica se a empresa que será atualizada existe
		
		return empresaRepository.save(empresa);
	}
	
	public void excluirConta(Integer id) {
		
		buscar(id);
		
		try {
			empresaRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Ainda não é possível excluir uma conta que contém outros registros", e.getCause());
		}
		
	}

}
