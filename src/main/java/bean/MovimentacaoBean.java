package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import converter.CategoriaConverter;
import entity.Categoria;
import entity.Conta;
import entity.Movimentacao;
import entity.Parcela;
import enums.TipoDeMovimentacao;
import repository.CategoriaDAO;
import repository.GenericoDAO;
import repository.MovimentacaoDAO;
import repository.ParcelaDAO;
import service.MovimentacaoService;
import util.FacesMessagesUtil;

@Named
@ViewScoped
public class MovimentacaoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MovimentacaoService movimentacaoService;
	
	@Inject 
	GenericoDAO genericoDAO;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@Inject
	private MovimentacaoDAO acaoDAO;
	
	@Inject
	private ParcelaDAO parcelaDAO;
	
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
		
	private List<Parcela> parcelas;
	
	private Parcela parcela;
	
	public void prepararNovo(Conta conta) {
		procurarTodasCategorias();
		movimentacao = new Movimentacao();
		movimentacao.setConta(conta);
		movimentacao.setTipoDeMovimentacao(TipoDeMovimentacao.SAIDA);
		movimentacao.setQuantidadeDeParcelas(1);
	}
	
	public void prepararEdicao(Movimentacao movimentacao) {
		procurarTodasCategorias();
		this.movimentacao = movimentacao;
	}
	
	public void salvarMovimentacao() {
		if(movimentacao.getId() != null) {
			movimentacaoService.salvarExistenteMovimentacao(movimentacao);
			
			this.procurarTodas(conta);
			this.conta = movimentacao.getConta();
			contaBean.procurarTodos();
			aplicarFiltro();
		}
		else {
			
			movimentacaoService.salvarNovaMovimentacao(movimentacao);
		}
		
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Movimentação Salva", "Movimentação Salva com Sucesso!");
		RequestContext.getCurrentInstance().update("mainForm:growl");
	}
	
	public void excluir(Movimentacao movimentacao) {
		
		movimentacaoService.excluirMovimentacao(movimentacao);
		
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
				
				parcelas = parcelaDAO.procurarPorContaEDataECategoria(conta, mes, ano, categoriaFiltro);
			}
			else if(categoriaFiltro == null) {
				parcelas = parcelaDAO.procurarPorContaEData(conta, mes, ano);
			}
		}
		
		else if( categoriaFiltro != null) {
			parcelas = parcelaDAO.procurarPorContaECategoria(conta, categoriaFiltro);
		}
		else {
			procurarPorDataAtualEConta(conta);
		}
		conta.setTotalMovimentado(parcelas);
	}
	
	public void procurarTodasCategorias() {
		this.categorias = categoriaDAO.procurarTodos();
	}
	
	public void maisDetalhes(Parcela parcela) {
		this.parcela = parcela;
	}
	
	public void procurarTodas(Conta conta) {
		this.conta = conta;
		this.movimentacoes = acaoDAO.procurarPorConta(conta.getId());
	}
	
	public void procurarPorDataAtualEConta(Conta conta) {
		this.parcelas = parcelaDAO.procurarPorContaEData(conta, data.getMonthValue(), data.getYear());
		conta.setTotalMovimentado(this.parcelas);
		
		this.conta = conta;
		
		this.dataFiltro = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
		System.out.println(dataFiltro);
		
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
			return true;
		}
		return false;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
}
