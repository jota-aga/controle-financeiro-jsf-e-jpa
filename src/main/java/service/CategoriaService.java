package service;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controller.GenericoController;
import entity.Categoria;
import entity.Movimentacao;
import repository.CategoriaDAO;
import repository.MovimentacaoDAO;
import util.FacesMessagesUtil;

public class CategoriaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	CategoriaDAO categoriaDAO;
	
	@Inject
	GenericoController genericoController;
	
	@Inject
	MovimentacaoDAO movimentacaoDAO;
	
	public void excluirCategoria(Categoria categoria) {
		List<Movimentacao> movimentacoes = movimentacaoDAO.procurarPorCategoria(categoria);
		
		if(movimentacoes != null && !movimentacoes.isEmpty()) {
			
			Categoria semCategoria = criarSemCategoria();
			
			for(Movimentacao m : movimentacoes) {
				
				m.setCategoria(semCategoria);
			}
			
			categoria.setMovimentacoes(movimentacoes);
			genericoController.salvar(categoria);
		}
		
		genericoController.excluir(Categoria.class, categoria.getId());
	}
	
	public boolean salvarCategoria(Categoria categoria) {
		Categoria categoriaRepetida = categoriaDAO.procurarPorTitulo(categoria.getTitulo());
		
		if(categoriaRepetida != null) {
			
			if(categoria.getId() == null || categoriaRepetida.getId() != categoria.getId()) {
				return false;
			}
		}
		
		genericoController.salvar(categoria);
		return true;
	}
	
	private Categoria criarSemCategoria() {
		Categoria semCategoria = categoriaDAO.procurarPorTitulo("Sem Categoria");
		
		if(semCategoria == null) {
			semCategoria = new Categoria();
			semCategoria.setTitulo("Sem Categoria");
			
			genericoController.salvar(semCategoria);
			semCategoria = categoriaDAO.procurarPorTitulo("Sem Categoria");
		}
		
		return semCategoria;
	}
}
