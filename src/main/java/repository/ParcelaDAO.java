package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Categoria;
import entity.Conta;
import entity.Parcela;

public class ParcelaDAO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public List<Parcela> procurarPorContaEData(Conta conta, int mes, int ano){
		String jpql = "select p from Parcela p where p.movimentacao.conta = :conta and MONTH(p.data) = :mes and YEAR(p.data) = :ano";
		Query q = em.createQuery(jpql);
		
		q.setParameter("conta", conta);
		q.setParameter("mes", mes);
		q.setParameter("ano", ano);
		
		return q.getResultList();
	}
	
	public List<Parcela> procurarPorContaEDataECategoria(Conta conta, int mes, int ano, Categoria categoria){
		String jpql = "select p from Parcela p where p.movimentacao.conta = :conta and MONTH(p.data) = :mes and YEAR(p.data) = :ano and p.movimentacao.categoria = :categoria";
		Query q = em.createQuery(jpql);
		
		q.setParameter("conta", conta);
		q.setParameter("mes", mes);
		q.setParameter("ano", ano);
		q.setParameter("categoria", categoria);
		
		return q.getResultList();
	}
	
	public List<Parcela> procurarPorContaECategoria(Conta conta, Categoria categoria){
		String jpql = "select p from Parcela p where p.movimentacao.conta = :conta and p.movimentacao.categoria = :categoria";
		Query q = em.createQuery(jpql);
		
		q.setParameter("conta", conta);
		q.setParameter("categoria", categoria);
		
		return q.getResultList();
	}
	
	public List<Parcela> procurarPorCategoria(Categoria categoria){
		String jpql = "select p from Parcela p where p.movimentacao.categoria = :categoria";
		Query q = em.createQuery(jpql);
		
		q.setParameter("categoria", categoria);
		
		return q.getResultList();
	}
	
	public List<Parcela> procurarPorMesEAno(int mes, int ano){
		String jpql = "select p from Parcela p where MONTH(p.data) = :mes and YEAR(p.data) = :ano";
		Query q = em.createQuery(jpql);
		
		q.setParameter("mes", mes);
		q.setParameter("ano", ano);
		
		return q.getResultList();
	}
}
