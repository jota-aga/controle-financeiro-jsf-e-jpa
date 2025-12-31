package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.ContaSaldo;
import entity.Movimentacao;
import enums.TipoDeConta;
import enums.TipoDeMovimentacao;
import repository.GenericoDAO;
import util.Transacional;

public class MovimentacaoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@Inject
	private GenericoDAO genericoDAO;
	
	@Transacional
	public void salvarNovaMovimentacao(Movimentacao movimentacao) {
		movimentacao.setTime(LocalTime.now());
		
		if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
			movimentacao.setTotalPrestacoes(1);
		}
		
		if(movimentacao.getConta().getTipoDeConta() != TipoDeConta.CREDITO && movimentacao.getConta().getContaSaldo() != null) {
			ContaSaldo contaSaldo = movimentacao.getConta().getContaSaldo();
			BigDecimal saldo = contaSaldo.getSaldo();
			BigDecimal valor = movimentacao.getValor();
			BigDecimal vezes = new BigDecimal(movimentacao.getTotalPrestacoes());
			
			if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
				saldo = saldo.add(valor.multiply(vezes));
			}
			else {
				saldo = saldo.subtract(valor.multiply(vezes));
			}
			
			contaSaldo.setSaldo(saldo);
		}
		
		for(int i = 1; i <= movimentacao.getTotalPrestacoes(); i++) {
			movimentacao.setData(LocalDate.now().plusMonths(i-1));
			movimentacao.setPrestacaoAtual(i);
			genericoDAO.salvar(movimentacao);
		}
	}
	
	@Transacional
	public void salvarExistenteMovimentacao(Movimentacao movimentacao) {
		Movimentacao movimentacaoAnterior = (Movimentacao) genericoDAO.procurarPorID(Movimentacao.class, movimentacao.getId());
		
		if(movimentacao.getConta().getTipoDeConta() != TipoDeConta.CREDITO && 
				(movimentacao.getTipoDeMovimentacao() != movimentacaoAnterior.getTipoDeMovimentacao() || 
				movimentacaoAnterior.getValor().compareTo(movimentacao.getValor()) != 0)) {
			ContaSaldo contaSaldo = movimentacao.getConta().getContaSaldo();
			BigDecimal saldo = contaSaldo.getSaldo();
			
			if(movimentacaoAnterior.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
				saldo = saldo.subtract(movimentacaoAnterior.getValor());
			}
			else {
				saldo = saldo.add(movimentacaoAnterior.getValor());
			}
			
			if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
				saldo = saldo.add(movimentacao.getValor());
			}
			else {
				saldo = saldo.subtract(movimentacao.getValor());
			}
			
			contaSaldo.setSaldo(saldo);
		}
		
		genericoDAO.salvar(movimentacao);
	}
	
	@Transacional
	public void excluirMovimentacao(Movimentacao movimentacao) {
		movimentacao = (Movimentacao) genericoDAO.procurarPorID(Movimentacao.class, movimentacao.getId());
		
		if(movimentacao.getConta().getContaSaldo() != null) {
			BigDecimal saldo = movimentacao.getConta().getContaSaldo().getSaldo();
			if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
				saldo = saldo.subtract(movimentacao.getValor());
			}
			else {
				saldo = saldo.add(movimentacao.getValor());
			}
			
			movimentacao.getConta().getContaSaldo().setSaldo(saldo);
		}
		
		genericoDAO.salvar(movimentacao.getConta());
		genericoDAO.excluir(Movimentacao.class, movimentacao.getId());
	}

}
