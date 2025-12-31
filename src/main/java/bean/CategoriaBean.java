package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Severity;

import org.primefaces.context.RequestContext;

import controller.CategoriaController;
import controller.GenericoController;
import entity.Categoria;
import entity.Movimentacao;
import repository.CategoriaDAO;
import repository.GenericoDAO;
import repository.MovimentacaoDAO;
import util.FacesMessagesUtil;

@Named
@ViewScoped
public class CategoriaBean implements Serializable{
	
	private Categoria categoria;
	
	private List<Categoria> categorias;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@Inject
	private GenericoController genericoController;
	
	@Inject
	private GenericoDAO genericoDAO;
	
	@Inject
	private CategoriaController categoriaController;
	
	@Inject
	private MovimentacaoDAO movimentacaoDAO;
	
	public void prepararNovo() {
		this.categoria = new Categoria();
	}
	
	public void prepararEdicao(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void salvarCategoria() {
		Categoria categoriaRepetida = categoriaDAO.procurarPorTitulo(categoria.getTitulo());
		
		if(categoriaRepetida != null) {
			if(categoria.getId() == null || categoriaRepetida.getId() != categoria.getId()) {
				FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_ERROR, null, "O Título informado já foi cadastrado");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
		}
		
		genericoController.salvar(categoria);
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Categoria Salva", "Categoria Salva com Sucesso");
		RequestContext.getCurrentInstance().update("mainForm:growl");
		procurarTodasCategorias();
	}
	
	public void excluir(Categoria categoria) {
		List<Movimentacao> movimentacoes = movimentacaoDAO.procurarPorCategoria(categoria);
		
		if(movimentacoes != null && !movimentacoes.isEmpty()) {
			Categoria semCategoria = categoriaController.criarSemCategoria();
			
			for(Movimentacao m : movimentacoes) {
				m.setCategoria(semCategoria);
			}
			categoria.setMovimentacoes(movimentacoes);
			genericoController.salvar(categoria);
		}
		genericoController.excluir(Categoria.class, categoria.getId());
		FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Categoria Excluída", "Categoria Excluída com Sucesso");
		RequestContext.getCurrentInstance().update("mainForm:growl");
		procurarTodasCategorias();
	}
	
	public void procurarTodasCategorias() {
		categorias = categoriaDAO.procurarTodos();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
}
