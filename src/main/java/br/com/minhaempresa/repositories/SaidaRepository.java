package br.com.minhaempresa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Saida;

public interface SaidaRepository extends JpaRepository<Saida, Integer>{
	
	@Transactional(readOnly = true)
	List<Saida> findByEmpresa(Empresa empresa); 

}
