package enums;

public enum TipoDeConta {
	DEBITO("Débito"),
	CREDITO("Crédito"),
	PIX("Pix");
	
	private String tipo;
	
	TipoDeConta(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
