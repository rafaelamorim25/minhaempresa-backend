package br.com.minhaempresa.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empresa_id")
	private Integer id;

	@Column(name = "empresa_cnpj", unique = true)
	private String cnpj;

	@Column(name = "empresa_nome_fantasia")
	private String nomeFantasia;

	@Column(name = "empresa_nome_proprietario")
	private String nomeProprietario;

	@Column(name = "empresa_telefone")
	private String telefone;

	@Column(name = "empresa_email", unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "empresa_senha")
	private String senha;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="Perfis")
	private Set<Integer> perfis = new HashSet<>();

	public Empresa() {
		addPerfil(Perfil.EMPRESA);
	}

	public Empresa(Integer id, String cnpj, String nomeFantasia, String nomeProprietario, String telefone,
			String email, String senha) {
		super();
		this.id = id;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.nomeProprietario = nomeProprietario;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.EMPRESA);
	}
	
	public Empresa(String cnpj, String nomeFantasia, String nomeProprietario, String telefone,
			String email, String senha) {
		super();
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.nomeProprietario = nomeProprietario;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.EMPRESA);
	}
	
	public Empresa(String cnpj, String nomeFantasia, String nomeProprietario, String telefone,
			String email) {
		super();
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.nomeProprietario = nomeProprietario;
		this.telefone = telefone;
		this.email = email;
		addPerfil(Perfil.EMPRESA);
	}

	public Empresa(Integer id) {
		super();
		this.id = id;
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
}
