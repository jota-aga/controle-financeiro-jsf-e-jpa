package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Conta;

public class ContaDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public void salvar(Conta conta) {
		em.merge(conta);
	}
	
	public void excluir(Conta conta) {
		conta = em.find(Conta.class, conta.getId());
		em.remove(conta);
	}
	
	public List<Conta> procurarTodos(){
		Query query = em.createQuery("from Conta");
		return query.getResultList();
	}
}
