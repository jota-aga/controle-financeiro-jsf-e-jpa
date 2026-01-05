package entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import enums.TipoDeMovimentacao;


@Entity
@Table(name = "movimentacao")
public class Movimentacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	@NotNull(message="Valor deve ser informado")
	@Min(message = "Valor deve ser maior que zero", value = 1)
	private BigDecimal valor;
	
	@Column
	@NotBlank(message = "Descrição não deve ser vazia")
	private String descricao;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "Tipo de Movimentação deve ser selecionada")
	private TipoDeMovimentacao tipoDeMovimentacao;
	
	@ManyToOne
	@NotNull(message = "Alguma categoria deve ser selecionada")
	private Categoria categoria;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Conta conta;
	
	@Min(value = 1, message = "Valor deve ser maior que zero")
	private int quantidadeDeParcelas;
	
	@OneToMany(mappedBy = "movimentacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Parcela> parcelas;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TipoDeMovimentacao getTipoDeMovimentacao() {
		return tipoDeMovimentacao;
	}

	public void setTipoDeMovimentacao(TipoDeMovimentacao tipoDeMovimentacao) {
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getQuantidadeDeParcelas() {
		return quantidadeDeParcelas;
	}

	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		this.quantidadeDeParcelas = quantidadeDeParcelas;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
	public void addParcela(Parcela parcela) {
		this.parcelas.add(parcela);
	}
	
	public void removeParcela(Parcela parcela) {
		this.parcelas.remove(parcela);
	}
}
