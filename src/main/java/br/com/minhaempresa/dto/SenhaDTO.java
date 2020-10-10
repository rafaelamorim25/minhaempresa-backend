package br.com.minhaempresa.dto;

import java.io.Serializable;

public class SenhaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String senha;
	
	public SenhaDTO() {}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
