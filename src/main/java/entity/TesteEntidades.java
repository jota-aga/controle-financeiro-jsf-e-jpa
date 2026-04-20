package entity;

import controller.GenericoController;
import enums.TipoDeConta;

public class TesteEntidades {
	public static void main(String[] args) {
		
		Conta conta = new Conta();
		
		conta.setTipoDeConta(TipoDeConta.PIX);
		conta.setBanco("Caixa");
		
		GenericoController controller = new GenericoController();
		
		controller.salvar(conta);
	}
}
