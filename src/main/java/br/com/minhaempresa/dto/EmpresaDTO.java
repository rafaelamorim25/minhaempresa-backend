package br.com.minhaempresa.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class EmpresaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Pattern(regexp = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}", message = "Preenchimento do cnpj é obrigatório, máscara: dd.ddd.ddd/dddd-dd")
	private String cnpj;

	@Length(min = 3, max = 255, message = "Preenchimento do nome fantasia é obrigatório e deve ter entre 3 e 255 caracteres")
	private String nomeFantasia;

	@Length(min = 3, max = 255, message = "Preenchimento do nome do proprietário é obrigatório e deve ter entre 3 e 255 caracteres")
	private String nomeProprietario;

	@NotEmpty(message = "Preenchimento do telefone é obrigatório")
	@Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}\\-\\d{4}", message = "Preenchimento do telefone é obrigatório, máscara: (62) 99195-5333")
	private String telefone;

	@Email
	private String email;

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
