package br.com.minhaempresa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
	
	@Transactional(readOnly = true)
	List<Cliente> findByCliente(Cliente cliente); 

}
