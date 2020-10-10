package br.com.minhaempresa.dto;

import java.io.Serializable;

public class ConfirmacaoSenhaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Boolean senhavalida;

	public ConfirmacaoSenhaDTO() {
	}

	public Boolean getSenhavalida() {
		return senhavalida;
	}

	public void setSenhavalida(Boolean senhavalida) {
		this.senhavalida = senhavalida;
	}
}
