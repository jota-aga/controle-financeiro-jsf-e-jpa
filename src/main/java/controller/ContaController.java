package controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Conta;
import util.Transacional;

public class ContaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	@Transacional
	public void salvar(Conta conta) {
		em.merge(conta);
	}
	
	@Transacional
	public void excluir(Conta conta) {
		em.remove(conta);
	}
}
