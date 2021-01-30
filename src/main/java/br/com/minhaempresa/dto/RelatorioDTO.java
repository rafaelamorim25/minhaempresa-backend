package br.com.minhaempresa.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer clientesCadastrados;
	private Integer quantidadeVendas;
	private Integer quantidadeRecebimentos;
	private Float ticketMedio;
	private Float totalVendas;
	private Float totalRecebimentos;
	private Float totalVendasPeriodo;
	private Float totalRecebimentosPeriodo;

}
