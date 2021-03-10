package br.com.minhaempresa.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Email(message = "Email é obrigatório")
	private String email;
	
}
