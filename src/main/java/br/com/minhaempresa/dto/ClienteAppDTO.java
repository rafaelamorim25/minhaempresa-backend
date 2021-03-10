package br.com.minhaempresa.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteAppDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull
	@Length(min = 3, max = 255, message = "Preenchimento do nome é obrigatório e deve ter entre 3 e 255 caracteres")
	private String nome;
	
	@NotNull
	@CPF
	private String cpf;
	
	@NotEmpty(message = "Preenchimento do telefone é obrigatório")
	@NotNull
	@Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}\\-\\d{4}", message = "Preenchimento do telefone é obrigatório, máscara: (62) 99195-5333")
	private String telefone;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Length(min = 8, max = 30, message = "A senha não pode ser vazia")
	private String senha;

}
