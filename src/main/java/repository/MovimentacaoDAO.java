package repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Categoria;
import entity.Movimentacao;

public class MovimentacaoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public void salvar(Movimentacao movimentacao) {
		em.merge(movimentacao);
	}
	
	public void excluir(Movimentacao movimentacao) {
		movimentacao = em.find(Movimentacao.class, movimentacao.getId());
		em.remove(movimentacao);
	}
	
	public List<Movimentacao> procurarTodos(){
		Query query = em.createQuery("from Movimentacao");
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorMesEAnoEConta(int mes, int ano, int idConta){
		Query query = em.createQuery("select m from Movimentacao m where MONTH(m.data) = :mes and YEAR(m.data) = :ano and m.conta.id = :idConta");
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
		query.setParameter("idConta", idConta);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorMesEAno(int mes, int ano){
		Query query = em.createQuery("select m from Movimentacao m where MONTH(m.data) = :mes and YEAR(m.data) = :ano");
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorMesEAnoEContaECategoria(int mes, int ano, int idConta, Categoria categoria){
		String jpql = "select m from Movimentacao m where MONTH(m.data) = :mes and YEAR(m.data) = :ano and m.conta.id = :idConta and m.categoria = :categoria";
		Query query = em.createQuery(jpql);
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
		query.setParameter("idConta", idConta);
		query.setParameter("categoria", categoria);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorContaECategoria(int idConta, Categoria categoria){
		String jpql = "select m from Movimentacao m where m.conta.id = :idConta and m.categoria = :categoria";
		Query query = em.createQuery(jpql);
		query.setParameter("idConta", idConta);
		query.setParameter("categoria", categoria);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorMes(int mes){
		Query query = em.createQuery("select m from Movimentacao m where MONTH(m.data) = :mes and m.conta.id = :idConta");
		query.setParameter("mes", mes);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorConta(int idConta){
		Query query = em.createQuery("select m from Movimentacao m where m.conta.id = :idConta");
		query.setParameter("idConta", idConta);
		return query.getResultList();
	}
	
	public List<Movimentacao> procurarPorCategoria( Categoria categoria){
		String jpql = "select m from Movimentacao m where m.categoria = :categoria";
		Query query = em.createQuery(jpql);
		query.setParameter("categoria", categoria);
		return query.getResultList();
	}
}
