package service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import javax.inject.Inject;

import controller.GenericoController;
import entity.ContaSaldo;
import entity.Movimentacao;
import entity.Parcela;
import enums.TipoDeConta;
import enums.TipoDeMovimentacao;
import repository.GenericoDAO;

public class MovimentacaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoController genericoController;
	
	@Inject
	private GenericoDAO genericoDAO;
	
	public void salvarNovaMovimentacao(Movimentacao movimentacao) {
		int quantidadeDeParcela = movimentacao.getQuantidadeDeParcelas();
		movimentacao.setParcelas(new ArrayList<>());
		
		for(int i = 0; i < quantidadeDeParcela; i++) {
			Parcela parcelas = new Parcela();
			
			parcelas.setData(LocalDate.now().plusMonths(i));
			parcelas.setHorario(LocalTime.now());
			parcelas.setParcela(i+1);
			parcelas.setMovimentacao(movimentacao);
			
			movimentacao.addParcela(parcelas);
		}
		
		alterarSaldo(movimentacao, false);
		genericoController.salvar(movimentacao);
	}
	
	public void salvarExistenteMovimentacao(Movimentacao movimentacao) {
		Movimentacao movimentacaoAnterior = (Movimentacao) genericoDAO.procurarPorID(Movimentacao.class, movimentacao.getId());
		
		boolean houveAlgumaMudanca = houveAlgumaMudanca(movimentacaoAnterior, movimentacao);
				
		if(houveAlgumaMudanca == true) {
			
			alterarSaldo(movimentacaoAnterior, true);
			
			if(movimentacao.getConta().getTipoDeConta() != TipoDeConta.CREDITO) {
				BigDecimal saldo = movimentacaoAnterior.getConta().getContaSaldo().getSaldo();
				
				movimentacao.getConta().getContaSaldo().setSaldo(saldo);
			}
			
						
			if(movimentacaoAnterior.getQuantidadeDeParcelas() != movimentacao.getQuantidadeDeParcelas()) {
				movimentacaoAnterior.getParcelas().sort(Comparator.comparing(Parcela::getData));
				
				if(movimentacao.getQuantidadeDeParcelas() < movimentacaoAnterior.getQuantidadeDeParcelas()) {
					int quantidadeDeParcelaDiminuidas = movimentacaoAnterior.getQuantidadeDeParcelas() - movimentacao.getQuantidadeDeParcelas();
					int ultimoIndex = movimentacao.getParcelas().size() - 1;
				
					for(int i = 0; i < quantidadeDeParcelaDiminuidas; i++) {
						movimentacao.getParcelas().remove(ultimoIndex - i);
						
					}
				}
				else {
					int quantidadeDeParcelaAumentadas = movimentacao.getQuantidadeDeParcelas() - movimentacaoAnterior.getQuantidadeDeParcelas();

					for(int i = 0; i < quantidadeDeParcelaAumentadas; i++) {
						Parcela ultimaParcela = movimentacao.getParcelas().get(movimentacao.getParcelas().size() - 1);
						
						Parcela novaParcela = new Parcela();
						novaParcela.setParcela(ultimaParcela.getParcela() + 1);
						novaParcela.setData(ultimaParcela.getData().plusMonths(1));
						novaParcela.setHorario(movimentacao.getParcelas().get(0).getHorario());
						novaParcela.setMovimentacao(movimentacao);
						
						movimentacao.addParcela(novaParcela);
						
					}
				}
			}
						
			alterarSaldo(movimentacao, false);		
		}
		
		genericoController.salvar(movimentacao);
	}
	
	public void excluirMovimentacao(Movimentacao movimentacao) {
		
		alterarSaldo(movimentacao, true);
		
		genericoController.salvar(movimentacao.getConta().getContaSaldo());
		
		genericoController.excluir(Movimentacao.class, movimentacao.getId());
	}
	
	private void alterarSaldo( Movimentacao movimentacao, boolean ehExclusao) {
		
		BigDecimal valorASerAplicado = movimentacao.getValor().multiply(new BigDecimal(movimentacao.getQuantidadeDeParcelas()));
		
		if(movimentacao.getConta().getTipoDeConta() != TipoDeConta.CREDITO && movimentacao.getConta().getContaSaldo() != null) {
			ContaSaldo contaSaldo = movimentacao.getConta().getContaSaldo();
			BigDecimal saldo = contaSaldo.getSaldo();
			
			if(!ehExclusao) {
				if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
					saldo = saldo.add(valorASerAplicado);
				}
				else {
					saldo = saldo.subtract(valorASerAplicado);
				}
			}
			else {
				if(movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
					saldo = saldo.subtract(valorASerAplicado);
				}
				else {
					saldo = saldo.add(valorASerAplicado);
				}
			}
			
			contaSaldo.setSaldo(saldo);
		}
	}
	
	private boolean houveAlgumaMudanca(Movimentacao movimentacaoAnterior, Movimentacao movimentacaoPresente) {
		
		if(movimentacaoAnterior.getValor() != movimentacaoPresente.getValor()) {
			return true;
		}
		else if(movimentacaoAnterior.getQuantidadeDeParcelas() != movimentacaoPresente.getQuantidadeDeParcelas()) {
			return true;
		}
		else if(movimentacaoAnterior.getValor().compareTo(movimentacaoPresente.getValor()) != 0) {
			return true;
		}
		
		return false;
	}
}
