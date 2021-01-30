package br.com.minhaempresa.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_id")
	private Integer id;

	@Column(name = "categoria_nome")
	private String nome;

	@Column(name = "categoria_descricao")
	private String descricao;
	
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
	@Builder.Default
	private Set<Saida> saidas = new HashSet<>();
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	
	public Categoria(String nome, String descricao, Empresa empresa) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.empresa = empresa;
	}
	
	public Categoria(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public Categoria(Integer id) {
		super();
		this.id = id;
	}
}
