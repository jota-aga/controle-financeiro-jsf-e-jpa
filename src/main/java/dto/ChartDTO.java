package dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Categoria;
import entity.Parcela;
import enums.TipoDeMovimentacao;

public class ChartDTO {
	private Map<String, Double> listaParaGraficoDeGastoPorCategoria;
	private Map<String, Double> listaParaGraficoDeGastoPorMes;
	
	public ChartDTO( ) {
		this.listaParaGraficoDeGastoPorCategoria = new HashMap<>();
		this.listaParaGraficoDeGastoPorMes = new HashMap<>();
	}

	public void adicionarGastosPorCategoria(Categoria categoria, List<Parcela> parcelas) {
		BigDecimal gasto = BigDecimal.ZERO;
		
		for(Parcela p : parcelas) {
			if(p.getMovimentacao().getTipoDeMovimentacao() == TipoDeMovimentacao.SAIDA) {
				gasto = gasto.add(p.getMovimentacao().getValor());
			}
			
		}
		
		listaParaGraficoDeGastoPorCategoria.put(categoria.getTitulo(), gasto.doubleValue());
	}
	
	public void adicionarGastoPorMes(List<Parcela> parcelas, int mes, int ano) {
		BigDecimal gasto = BigDecimal.ZERO;
		
		if(parcelas != null && !parcelas.isEmpty()) {
			for(Parcela p : parcelas) {
				if(p.getMovimentacao().getTipoDeMovimentacao() == TipoDeMovimentacao.SAIDA)
					gasto = gasto.add(p.getMovimentacao().getValor());
			}
			
			StringBuilder mesEAno = new StringBuilder(String.valueOf(mes) + "/" + String.valueOf(ano));
			
			listaParaGraficoDeGastoPorMes.put(mesEAno.toString(), gasto.doubleValue());
		}
	}
	
	public String getJsonParaGraficoGastosPorCategoria() {
		StringBuilder json = new StringBuilder("[");
		json.append("['Categoria', 'Gastos'],");
		
		for(Map.Entry<String, Double> entry : listaParaGraficoDeGastoPorCategoria.entrySet()) {
			json.append("['" + entry.getKey() + "'," + entry.getValue() + "],");
		}
		
		int indexDaUltimaVirgula =  json.lastIndexOf(",");
		json.deleteCharAt(indexDaUltimaVirgula);
		
		json.append("]");
		
		return json.toString();
	}
	
	public String getJsonParaGraficoGastosPorMes() {
		StringBuilder json = new StringBuilder("[");
		json.append("['Mês', 'Gastos'],");
		
		for(Map.Entry<String, Double> entry : listaParaGraficoDeGastoPorMes.entrySet()) {
			json.append("['" + entry.getKey() + "'," + entry.getValue() + "],"); 
		}
		
		int indexDaUltimaVirgula = json.lastIndexOf(",");
		json.deleteCharAt(indexDaUltimaVirgula);
		
		json.append("]");
		return json.toString();
	}
}
