package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import entity.Categoria;
import repository.CategoriaDAO;
import service.CategoriaService;
import util.FacesMessagesUtil;

@Named
@ViewScoped
public class CategoriaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	
	private List<Categoria> categorias;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@Inject
	private CategoriaService categoriaService;
	
	public void prepararNovo() {
		this.categoria = new Categoria();
	}
	
	public void prepararEdicao(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void salvarCategoria() {
		
		boolean salvoComSucesso = categoriaService.salvarCategoria(categoria);
		
		if(salvoComSucesso) {
			FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_INFO, "Categoria Salva", "Categoria Salva com Sucesso");
			RequestContext.getCurrentInstance().update("mainForm:growl");
			procurarTodasCategorias();
		}
		else {
			FacesMessagesUtil.addMessageSemId(FacesMessage.SEVERITY_ERROR, null, "O Título informado já foi cadastrado");
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void excluir(Categoria categoria) {
		
		categoriaService.excluirCategoria(categoria);
		
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
