package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.ChartDTO;
import entity.Categoria;
import entity.Parcela;
import repository.CategoriaDAO;
import repository.MovimentacaoDAO;
import repository.ParcelaDAO;

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
	
	@Inject
	ParcelaDAO parcelasDAO;
	
	private List<Categoria> categorias;
	
	private ChartDTO chartDTO;
	
	public void prepararChartDTO() {
		chartDTO = new ChartDTO();
	}
	
	public void procurarTodasCategorias() {
		categorias = categoriaDAO.procurarTodos();
	}
	
	public void pupularGraficoDeGastosPorCategoria() {
		for(Categoria c : categorias) {
			List<Parcela> parcelas = parcelasDAO.procurarPorCategoria(c);
			
			if(parcelas != null && !parcelas.isEmpty()) {
				chartDTO.adicionarGastosPorCategoria(c, parcelas);
			}
		}
	}
	
	public void pupularGraficoDeGastosPorPeriodo() {
		int meses = 5;
		
		LocalDate data = LocalDate.now();
		
		for(int  i = 0; i < meses; i++) {
			int mes = data.getMonthValue();
			int ano = data.getYear();
			data = data.minusMonths(1);
			
			List<Parcela> parcelas = parcelasDAO.procurarPorMesEAno(mes, ano);
			
			chartDTO.adicionarGastoPorMes(parcelas, mes, ano);
		}
	}
	
	public void gerarGraficos() {
		prepararChartDTO();
		procurarTodasCategorias();
		pupularGraficoDeGastosPorCategoria();
		pupularGraficoDeGastosPorPeriodo();
	}

	public ChartDTO getChartDTO() {
		return chartDTO;
	}
}
