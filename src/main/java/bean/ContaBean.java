package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import controller.GenericoController;
import entity.Conta;
import entity.ContaSaldo;
import enums.TipoDeConta;
import repository.ContaDAO;
import repository.ContaSaldoDAO;
import repository.GenericoDAO;
import util.FacesMessagesUtil;

@Named
@ViewScoped
public class ContaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContaDAO contaDAO;
	
	@Inject
	private ContaSaldoDAO contaSaldoDAO;
	
	@Inject 
	private GenericoController controller;
	
	@Inject
	private GenericoDAO genericoDAO;
	
	private List<Conta> contas;
	
	private Conta conta;
	
	private BigDecimal saldo;
	
	private ContaSaldo contaSaldo;
		
	public void prepararNovo() {
		conta = new Conta();
	}
	
	public void prepararEdicao(Conta conta) {
		this.conta = conta;
		
		if(conta.getTipoDeConta() != TipoDeConta.CREDITO) {
			saldo = conta.getContaSaldo().getSaldo();
		}
	}
	
	public void salvarConta() {
		
		if(this.conta.getTipoDeConta() != TipoDeConta.CREDITO) {
			if(this.conta.getId() == null || this.conta.getContaSaldo() == null) {
				contaSaldo = new ContaSaldo();
				conta.setContaSaldo(contaSaldo);
				contaSaldo.setConta(conta);
			}
			
			conta.getContaSaldo().setSaldo(saldo);
			
		}
		
		if(conta.getId() != null) {
			if(this.conta.getTipoDeConta() == TipoDeConta.CREDITO && this.conta.getContaSaldo() != null) {
				contaSaldo = this.conta.getContaSaldo();
				
				this.conta.setContaSaldo(null);
				this.contaSaldo.setConta(null);
				controller.salvar(contaSaldo);
				controller.excluir(ContaSaldo.class, contaSaldo.getId());
			}
		}
		
		controller.salvar(conta);
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Conta Salva", "Conta Salva Com Sucesso!");
		RequestContext.getCurrentInstance().update("mainForm:growl");
		this.procurarTodos();
	}
	
	public void excluir(Conta conta) {
		controller.excluir(conta.getClass(), conta.getId());
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Conta Excluída", "Conta Excluída Com Sucesso!");
		this.procurarTodos();
	}
	
	public void procurarTodos() {
		this.contas = contaDAO.procurarTodos();
	}

	public List<Conta> getContas() {
		return contas;
	}
	
	public TipoDeConta[] getTiposDeConta(){
		return TipoDeConta.values();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public boolean isContaPoupanca() {
		return (conta.getTipoDeConta() == TipoDeConta.DEBITO || conta.getTipoDeConta() == TipoDeConta.PIX) ? true : false;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
