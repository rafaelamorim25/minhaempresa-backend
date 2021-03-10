package br.com.minhaempresa.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaidaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull
	@Length(min = 3, max = 255, message = "Preenchimento do nome é obrigatório e deve ter entre 3 e 255 caracteres")
	private String descricao;
	
	@NotNull
	private Date data;
	
	@NotNull
	@Min(value = 1, message = "Valor mínimo é 1")
	private Float valor;
	
	private CategoriaDTO categoria;

}
