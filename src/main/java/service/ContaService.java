package service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import controller.GenericoController;
import entity.Conta;
import entity.ContaSaldo;
import enums.TipoDeConta;


public class ContaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericoController genericoController;
	
	public void salvarConta(Conta conta, BigDecimal saldo) {
		criarContaSaldo(conta, saldo);
		
		contaExistenteParaCredito(conta);
		
		genericoController.salvar(conta);
	}
	
	private void criarContaSaldo(Conta conta, BigDecimal saldo) {
		if(conta.getTipoDeConta() != TipoDeConta.CREDITO) {
			if(conta.getId() == null || conta.getContaSaldo() == null) {
				ContaSaldo contaSaldo = new ContaSaldo();
				conta.setContaSaldo(contaSaldo);
				contaSaldo.setConta(conta);
			}
			
			conta.getContaSaldo().setSaldo(saldo);
			
		}
	}
	
	private void contaExistenteParaCredito(Conta conta) {
		if(conta.getId() != null) {
			if(conta.getTipoDeConta() == TipoDeConta.CREDITO && conta.getContaSaldo() != null) {
				ContaSaldo contaSaldo = conta.getContaSaldo();
				
				conta.setContaSaldo(null);
				contaSaldo.setConta(null);
				genericoController.salvar(contaSaldo);
				genericoController.excluir(ContaSaldo.class, contaSaldo.getId());
			}
		}
	}
}
