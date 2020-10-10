package br.com.minhaempresa.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Email(message = "Email é obrigatório")
	private String email;
	
	public EmailDTO() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
