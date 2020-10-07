package br.com.minhaempresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.minhaempresa.domain.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{

}
