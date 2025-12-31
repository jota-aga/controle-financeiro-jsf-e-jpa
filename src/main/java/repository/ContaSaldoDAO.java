package repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.ContaSaldo;

public class ContaSaldoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManager em;
	
	public ContaSaldo procurarContaSaldoPorContaId(Integer contaId) {
		Query q = em.createQuery("select c from ContaSaldo c where c.conta.id = :contaId");
		q.setParameter("contaId", contaId);
		ContaSaldo contaSaldo = (ContaSaldo) q.getSingleResult();
		if(contaSaldo == null) {
			System.out.println("vazioooooooooooooooooo");
		}
		else {
			System.out.println("TEMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		}
		return (ContaSaldo) q.getSingleResult();
	}
}
