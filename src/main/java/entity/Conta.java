package entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import enums.TipoDeConta;
import enums.TipoDeMovimentacao;

@Entity
@Table(name="conta")
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	@NotBlank(message = "Banco não deve ser vazio")
	private String banco;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O Tipo de Conta deve ser especificado")
	private TipoDeConta tipoDeConta;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conta")
	private List<Movimentacao> acoes;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "conta")
	private ContaSaldo contaSaldo;
	
	@Transient
	private BigDecimal totalMovimentado;
	
	@Transient 
	private BigDecimal entrou;
	
	@Transient 
	private BigDecimal saiu;
	
	@Transient
	private String feedback;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public List<Movimentacao> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Movimentacao> acoes) {
		this.acoes = acoes;
	}

	public TipoDeConta getTipoDeConta() {
		return tipoDeConta;
	}

	public void setTipoDeConta(TipoDeConta tipoDeConta) {
		this.tipoDeConta = tipoDeConta;
	}

	public ContaSaldo getContaSaldo() {
		return contaSaldo;
	}

	public void setContaSaldo(ContaSaldo contaSaldo) {
		this.contaSaldo = contaSaldo;
	}

	public BigDecimal getTotalMovimentado() {
		return totalMovimentado;
	}

	public void setTotalMovimentado(List<Parcela> parcelas) {
		totalMovimentado = BigDecimal.ZERO;
		saiu = BigDecimal.ZERO;
		entrou = BigDecimal.ZERO;
		
		for(Parcela p : parcelas) {
			BigDecimal valor = p.getMovimentacao().getValor();
			if(p.getMovimentacao().getTipoDeMovimentacao() == TipoDeMovimentacao.SAIDA) {
				saiu = saiu.add(valor);
			}
			else {
				entrou = entrou.add(valor);
			}
		}
		
		if(saiu.compareTo(entrou) == 1) {
			totalMovimentado = saiu.subtract(entrou);
			feedback = "Gastou";
		}
		else {
			totalMovimentado = entrou.subtract(saiu);
			feedback = "Economizou";
		}
	}

	public BigDecimal getEntrou() {
		return entrou;
	}

	public void setEntrou(BigDecimal entrou) {
		this.entrou = entrou;
	}

	public BigDecimal getSaiu() {
		return saiu;
	}

	public void setSaiu(BigDecimal saiu) {
		this.saiu = saiu;
	}

	public String getFeedback() {
		return feedback;
	}
}
