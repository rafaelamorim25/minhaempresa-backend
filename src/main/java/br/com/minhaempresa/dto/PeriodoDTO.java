package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Date dataInicial;
	
	@NotNull
	private Date dataFinal;
}
