package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private Float saldo;
	
	@Builder.Default
	private Set<VendaDTO> vendas = new HashSet<>();
	
	@Builder.Default
	private Set<RecebimentoDTO> recebimentos = new HashSet<>();

}
