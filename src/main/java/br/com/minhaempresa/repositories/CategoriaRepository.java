package br.com.minhaempresa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.minhaempresa.domain.Categoria;
import br.com.minhaempresa.domain.Empresa;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	@Transactional(readOnly = true)
	List<Categoria> findByEmpresa(Empresa empresa); 
	
	@Transactional
	void deleteByEmpresa(Empresa empresa);

}
