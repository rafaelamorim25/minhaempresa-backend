package br.com.minhaempresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.minhaempresa.domain.ClienteApp;

@Repository
public interface ClienteAppRepository extends JpaRepository<ClienteApp, Integer>{
	
	@Transactional(readOnly = true)
	ClienteApp findByEmail(String email);

}
