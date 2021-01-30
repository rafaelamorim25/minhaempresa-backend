package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.minhaempresa.domain.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Date data;
	private Float valor;
	
	@Builder.Default
	private Boolean estornada = false;
	
	private FormaPagamento formaPagamento;
	
	private ClienteDTO cliente;

}
