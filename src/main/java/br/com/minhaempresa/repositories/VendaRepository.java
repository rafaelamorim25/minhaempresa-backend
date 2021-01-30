package br.com.minhaempresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.minhaempresa.domain.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

}
