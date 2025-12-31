package enums;

public enum TipoDeConta {
	DEBITO("Débito"),
	CREDITO("Crédito"),
	PIX("Pix");
	
	private String tipoDeConta;
	
	TipoDeConta(String tipoDeConta){
		this.tipoDeConta = tipoDeConta;
	}

	public String getTipoDeConta() {
		return tipoDeConta;
	}
}
