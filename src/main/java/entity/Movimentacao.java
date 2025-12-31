package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
	@Min(message = "O total de prestações deve ser maior que zero", value = 1)
	private int totalPrestacoes;
	
	@Column
	private int prestacaoAtual;
	
	@Column
	private LocalDate data;
	
	@Column
	private LocalTime time;
	
	@Column
	@NotBlank(message = "Descrição não deve ser vazia")
	private String descricao;
	
	@ManyToOne
	@NotNull(message = "Alguma categoria deve ser selecionada")
	private Categoria categoria;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Conta conta;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "Tipo de Movimentação deve ser selecionada")
	private TipoDeMovimentacao tipoDeMovimentacao;

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

	public int getTotalPrestacoes() {
		return totalPrestacoes;
	}

	public void setTotalPrestacoes(int totalPrestacoes) {
		this.totalPrestacoes = totalPrestacoes;
	}

	public String getData() {
		return data.format(DateTimeFormatter.ofPattern("d/MM/yyyy"));
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getDiaDaSemana() {
		return data.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("PT", "BR"));
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
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

	public int getPrestacaoAtual() {
		return prestacaoAtual;
	}

	public void setPrestacaoAtual(int prestacaoAtual) {
		this.prestacaoAtual = prestacaoAtual;
	}
}
