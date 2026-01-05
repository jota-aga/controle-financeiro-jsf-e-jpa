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
import enums.TipoDeConta;
import repository.ContaDAO;
import service.ContaService;
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
	private GenericoController genericoController;
	
	@Inject
	private ContaService contaService;
	
	private List<Conta> contas;
	
	private Conta conta;
	
	private BigDecimal saldo;
		
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
		
		contaService.salvarConta(conta, saldo);
		
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Conta Salva", "Conta Salva Com Sucesso!");
		RequestContext.getCurrentInstance().update("mainForm:growl");
		this.procurarTodos();
	}
	
	public void excluir(Conta conta) {
		genericoController.excluir(conta.getClass(), conta.getId());
		
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
