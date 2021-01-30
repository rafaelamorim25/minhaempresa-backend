package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecebimentoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date data;
	private Float valor;
	
	@Builder.Default
	private Boolean estornada = false;
	
	private ClienteDTO cliente;

}
