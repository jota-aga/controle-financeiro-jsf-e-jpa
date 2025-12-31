package repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class GenericoDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public Object salvar(Object t) {
		return em.merge(t);
	}
	
	public void excluir(Class<?> classe , Integer id) {
		Object t = this.procurarPorID(classe, id);
		em.remove(t);
	}
	
	public Object procurarPorID(Class<?> classe, Integer id) {
		Object t = em.find(classe, id);
	
		return t;
	}
}
