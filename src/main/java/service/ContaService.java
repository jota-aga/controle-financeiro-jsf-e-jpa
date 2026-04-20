package service;

import java.io.Serializable;

import javax.inject.Inject;

import controller.GenericoController;
import entity.Conta;


public class ContaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericoController genericoController;
	
	public void salvarConta(Conta conta) {
		genericoController.salvar(conta);
	}
	
}
