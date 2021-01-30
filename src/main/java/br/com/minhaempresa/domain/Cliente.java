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
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Integer id;
	
	@Column(name = "cliente_nome")
	private String nome;
	
	@Column(name = "cliente_cpf")
	private String cpf;
	
	@Column(name = "cliente_telefone")
	private String telefone;
	
	@Column(name = "cliente_email")
	private String email;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private Set<Venda> vendas = new HashSet<>();
	
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private Set<Recebimento> recebimentos = new HashSet<>();
	
	public Cliente(Integer id) {
		this.id = id;
	}
	
	public Float getSaldo() {
		
		Float sumVendas = 0F, sumRecebimentos = 0F;
		
		if(this.vendas != null && !this.vendas.isEmpty()) {
			
			for(Venda v: this.vendas) {
				if(v.getFormaPagamento().getDescricao().equals("A prazo")) {
					sumVendas = sumVendas + v.getValor();
				}
			}	
		}
		
		if(this.recebimentos != null && !this.recebimentos.isEmpty()) {
			sumRecebimentos = this.recebimentos.stream()
					.map(r -> r.getValor())
					.reduce((a, b) -> a + b)
					.get();
		}
		
		return sumVendas - sumRecebimentos;
	}

}
