package entity;

import java.math.BigDecimal;

import controller.GenericoController;
import enums.TipoDeConta;

public class TesteEntidades {
	public static void main(String[] args) {
		
		Conta conta = new Conta();
		
		conta.setTipoDeConta(TipoDeConta.PIX);
		conta.setBanco("Caixa");
		
		ContaSaldo contaSaldo = new ContaSaldo();
		
		contaSaldo.setSaldo(new BigDecimal(100));
		
		conta.setContaSaldo(contaSaldo);
		
		GenericoController controller = new GenericoController();
		
		controller.salvar(conta);
	}
}
