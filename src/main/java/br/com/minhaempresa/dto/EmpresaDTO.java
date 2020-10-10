package br.com.minhaempresa.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class EmpresaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull
	@CNPJ
	private String cnpj;

	@NotNull
	@Length(min = 3, max = 255, message = "Preenchimento do nome fantasia é obrigatório e deve ter entre 3 e 255 caracteres")
	private String nomeFantasia;

	@NotNull
	@Length(min = 3, max = 255, message = "Preenchimento do nome do proprietário é obrigatório e deve ter entre 3 e 255 caracteres")
	private String nomeProprietario;

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
	
	public EmpresaDTO() {}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
