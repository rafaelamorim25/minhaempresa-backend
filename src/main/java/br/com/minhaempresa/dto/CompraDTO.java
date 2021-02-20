package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	private String nomeFantasia;
	private String telefone;
	private Float divida;
	private Boolean visualizar;
	
	private Set<VendaDTO> compras;
	private Set<RecebimentoDTO> pagamentos;
	private Integer clienteId;
	
}
