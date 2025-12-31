package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import controller.GenericoController;
import controller.MovimentacaoController;
import converter.CategoriaConverter;
import entity.Categoria;
import entity.Conta;
import entity.ContaSaldo;
import entity.Movimentacao;
import enums.TipoDeConta;
import enums.TipoDeMovimentacao;
import repository.CategoriaDAO;
import repository.GenericoDAO;
import repository.MovimentacaoDAO;
import util.FacesMessagesUtil;

@Named
@ViewScoped
public class MovimentacaoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoController genericoController;
	
	@Inject
	private MovimentacaoController movimentacaoController;
	
	@Inject 
	GenericoDAO genericoDAO;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@Inject
	private MovimentacaoDAO acaoDAO;
	
	@Inject
	ContaBean contaBean;
	
	private CategoriaConverter categoriaConverter;
	
	private List<Categoria> categorias;
	
	private Movimentacao movimentacao;
	
	private List<Movimentacao> movimentacoes;
	
	private Conta conta;
		
	private String dataFiltro;
	
	private Categoria categoriaFiltro;
	
	private LocalDate data = LocalDate.now();
	
	public void prepararNovo(Conta conta) {
		procurarTodasCategorias();
		movimentacao = new Movimentacao();
		movimentacao.setConta(conta);
		movimentacao.setTipoDeMovimentacao(TipoDeMovimentacao.SAIDA);
		movimentacao.setTotalPrestacoes(1);
	}
	
	public void prepararEdicao(Movimentacao movimentacao) {
		procurarTodasCategorias();
		this.movimentacao = movimentacao;
	}
	
	public void salvarMovimentacao() {
		if(movimentacao.getId() != null) {
			movimentacaoController.salvarExistenteMovimentacao(movimentacao);
			this.procurarTodas(conta);
			this.conta = movimentacao.getConta();
			contaBean.procurarTodos();
			aplicarFiltro();
		}
		else {
			movimentacaoController.salvarNovaMovimentacao(movimentacao);
		}
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Movimentação Salva", "Movimentação Salva com Sucesso!");
		RequestContext.getCurrentInstance().update("mainForm:growl");
	}
	
	public void excluir(Movimentacao movimentacao) {
		
		movimentacaoController.excluirMovimentacao(movimentacao);
		
		this.procurarTodas(conta);
		this.conta = movimentacao.getConta();
		contaBean.procurarTodos();
		aplicarFiltro();
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Movimentação Excluída", "Movimentação Excluída com Sucesso!");

	}
	
	public void aplicarFiltro() {
		int ano, mes;
		
		if(dataFiltro != null && !dataFiltro.isEmpty()) {
			String[] textoData = dataFiltro.split("/");
			mes = Integer.valueOf(textoData[0]);
			ano = Integer.valueOf(textoData[1]);
			if(categoriaFiltro != null) {
				
				movimentacoes = acaoDAO.procurarPorMesEAnoEContaECategoria(mes, ano, conta.getId(), categoriaFiltro);
			}
			else if(categoriaFiltro == null) {
				movimentacoes = acaoDAO.procurarPorMesEAnoEConta(mes, ano, conta.getId());
			}
		}
		
		else if( categoriaFiltro != null) {
			movimentacoes = acaoDAO.procurarPorContaECategoria(conta.getId(), categoriaFiltro);
		}
		else {
			procurarPorDataAtualEConta(conta);
		}
		conta.setTotalMovimentado(movimentacoes);
	}
	
	public void procurarTodasCategorias() {
		this.categorias = categoriaDAO.procurarTodos();
	}
	
	public void maisDetalhes(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}
	
	public void procurarTodas(Conta conta) {
		this.conta = conta;
		this.movimentacoes = acaoDAO.procurarPorConta(conta.getId());
	}
	
	public void procurarPorDataAtualEConta(Conta conta) {
		this.movimentacoes = acaoDAO.procurarPorMesEAnoEConta(data.getMonthValue(), data.getYear(), conta.getId());
		conta.setTotalMovimentado(this.movimentacoes);
		
		this.conta = conta;
		this.dataFiltro = String.valueOf(data.getMonthValue()) + "/" + String.valueOf(data.getYear());
		procurarTodasCategorias();
	}

	public CategoriaConverter getCategoriaConverter() {
		categoriaConverter = new CategoriaConverter(categorias);
		return categoriaConverter;
	}
	
	public TipoDeMovimentacao[] getTiposDeMovimentacao() {
		return TipoDeMovimentacao.values();
	}
	
	public List<Categoria> getCategorias(){
		return categorias;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public Conta getConta() {
		return conta;
	}
	
	public boolean isEntrada() {
		return this.movimentacao.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA? true :  false;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(String dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public Categoria getCategoriaFiltro() {
		return categoriaFiltro;
	}

	public void setCategoriaFiltro(Categoria categoriaFiltro) {
		this.categoriaFiltro = categoriaFiltro;
	}
	
	public Categoria getCategoriaVazia() {
		Categoria categoriaVazia = null;
		return categoriaVazia;
	}
	
	public boolean ehEdicao() {
		
		if(movimentacao.getId() != null) {
			System.out.println("BOOOOOOOOOOOOOOOOLEAN:" + true);
			return true;
		}
		System.out.println("BOOOOOOOOOOOOOOOOLEAN:" + false);
		return false;
	}
}
