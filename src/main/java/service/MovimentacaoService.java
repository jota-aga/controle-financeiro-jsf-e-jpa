package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.inject.Inject;

import controller.GenericoController;
import entity.Movimentacao;
import entity.Parcela;
import repository.GenericoDAO;

public class MovimentacaoService implements Serializable {

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

		for (int i = 0; i < quantidadeDeParcela; i++) {
			Parcela parcelas = new Parcela();

			parcelas.setData(LocalDate.now().plusMonths(i));
			parcelas.setHorario(LocalTime.now());
			parcelas.setParcela(i + 1);
			parcelas.setMovimentacao(movimentacao);

			movimentacao.addParcela(parcelas);
		}

		genericoController.salvar(movimentacao);
	}

	public void salvarExistenteMovimentacao(Movimentacao movimentacao) {
		Movimentacao movimentacaoAnterior = (Movimentacao) genericoDAO.procurarPorID(Movimentacao.class, movimentacao.getId());
		
		int qtdParcelasAtuais = movimentacao.getQuantidadeDeParcelas();
		int qtdParcelasAnteoriores = movimentacaoAnterior.getQuantidadeDeParcelas();

		if (qtdParcelasAtuais != qtdParcelasAnteoriores) {

			if (qtdParcelasAtuais < qtdParcelasAnteoriores) {
				int qtdDeParcelaDiminuidas = movimentacaoAnterior.getQuantidadeDeParcelas() - movimentacao.getQuantidadeDeParcelas();
				int ultimoIndex = movimentacao.getParcelas().size() - 1;

				for (int i = 0; i < qtdDeParcelaDiminuidas; i++) {
					movimentacao.getParcelas().remove(ultimoIndex - i);
				}
			} 
			
			else {
				int quantidadeDeParcelaAumentadas = movimentacao.getQuantidadeDeParcelas() - movimentacaoAnterior.getQuantidadeDeParcelas();

				for (int i = 0; i < quantidadeDeParcelaAumentadas; i++) {
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

		genericoController.salvar(movimentacao);
	}

	public void excluirMovimentacao(Movimentacao movimentacao) {
		genericoController.excluir(Movimentacao.class, movimentacao.getId());
	}
}
