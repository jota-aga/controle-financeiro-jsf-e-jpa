package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.ChartDTO;
import entity.Categoria;
import entity.Movimentacao;
import repository.CategoriaDAO;
import repository.MovimentacaoDAO;

@Named
@ViewScoped
public class ChartBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	CategoriaDAO categoriaDAO;
	
	@Inject
	MovimentacaoDAO movimentacaoDAO;
	
	private List<Categoria> categorias;
	
	private ChartDTO chartDTO;
	
	public void prepararChartDTO() {
		chartDTO = new ChartDTO();
	}
	
	public void procurarTodasCategorias() {
		categorias = categoriaDAO.procurarTodos();
	}
	
	public void gerarGraficoPorCategoria() {
		for(Categoria c : categorias) {
			List<Movimentacao> movimentacoes = movimentacaoDAO.procurarPorCategoria(c);
			
			if(movimentacoes != null && !movimentacoes.isEmpty()) {
				chartDTO.adicionarGastosPorCategoria(c, movimentacoes);
			}
		}
	}
	
	public void gerarMovimentacoesPorPeriodo() {
		LocalDate data = LocalDate.now().plusMonths(1);
		for(int  i = 0; i < 5; i++) {
			int mes = data.getMonthValue();
			int ano = data.getYear();
			data = data.minusMonths(1);
			
			List<Movimentacao> movimentacoes = movimentacaoDAO.procurarPorMesEAno(mes, ano);
			
			chartDTO.adicionarGastoPorMes(movimentacoes, mes, ano);
		}
	}
	
	public void gerarGraficos() {
		prepararChartDTO();
		procurarTodasCategorias();
		gerarGraficoPorCategoria();
		gerarMovimentacoesPorPeriodo();
	}

	public ChartDTO getChartDTO() {
		return chartDTO;
	}
}
