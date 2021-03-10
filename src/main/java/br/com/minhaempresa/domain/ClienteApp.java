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
public class ClienteApp implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clienteApp_id")
	private Integer id;
	
	@Column(name = "clienteApp_nome")
	private String nome;
	
	@Column(name = "clienteApp_cpf", unique = true)
	private String cpf;
	
	@Column(name = "clienteApp_telefone")
	private String telefone;
	
	@Column(name = "clienteApp_email", unique = true)
	private String email;
	
	@JsonIgnore
	@Column(name = "clienteApp_senha")
	private String senha;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="Perfis")
	private Set<Integer> perfis = new HashSet<>();
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public ClienteApp(){
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteApp(Integer id, String nome, String cpf, String telefone, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}
	
	public ClienteApp(String nome, String cpf, String telefone, String email, String senha) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}
	
	public ClienteApp(String nome, String telefone, String email) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		addPerfil(Perfil.CLIENTE);
	}
	
	public ClienteApp(Integer id) {
		super();
		this.id = id;
		addPerfil(Perfil.CLIENTE);
	}
}
