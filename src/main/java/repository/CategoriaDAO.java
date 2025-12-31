package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Categoria;

public class CategoriaDAO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
	
	
	public List<Categoria> procurarTodos(){
		Query q = em.createQuery("from Categoria");
		
		return q.getResultList();
	}


	public Categoria procurarPorTitulo(String titulo) {
		try{
			String jpql = "select c from Categoria c where c.titulo = :titulo";
			Query q = em.createQuery(jpql);
			q.setParameter("titulo", titulo);
			return (Categoria) q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
}
