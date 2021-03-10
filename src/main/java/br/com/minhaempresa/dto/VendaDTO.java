package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private String descricao;
	
	@NotNull
	private Date data;
	
	@NotNull
	@Min(value = 1, message = "Valor mínimo é 1")
	private Float valor;
	
	@Builder.Default
	private Boolean estornada = false;
	
	@NotNull
	private FormaPagamento formaPagamento;
	
	@NotNull
	private ClienteDTO cliente;

}
