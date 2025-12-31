package controller;

import java.io.Serializable;

import javax.inject.Inject;

import repository.GenericoDAO;
import util.Transacional;

public class GenericoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GenericoDAO genericoDAO;
	
	@Transacional
	public void salvar(Object t) {
		genericoDAO.salvar(t);
	}
	
	@Transacional
	public void excluir(Class<?> classe, Integer id) {
		genericoDAO.excluir(classe, id);
	}
}
