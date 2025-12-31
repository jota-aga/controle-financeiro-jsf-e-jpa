package controller;

import java.io.Serializable;

import javax.inject.Inject;

import entity.Categoria;
import repository.CategoriaDAO;
import repository.GenericoDAO;
import util.Transacional;

public class CategoriaController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	CategoriaDAO categoriaDAO;
	
	@Inject
	GenericoDAO genericoDAO;
	
	@Transacional
	public Categoria criarSemCategoria() {
		Categoria semCategoria = categoriaDAO.procurarPorTitulo("Sem Categoria");
		
		if(semCategoria == null) {
			semCategoria = new Categoria();
			semCategoria.setTitulo("Sem Categoria");
			
			genericoDAO.salvar(semCategoria);
			semCategoria = categoriaDAO.procurarPorTitulo("Sem Categoria");
		}
		
		return semCategoria;
	}
}
