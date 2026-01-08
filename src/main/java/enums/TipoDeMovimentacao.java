package enums;

public enum TipoDeMovimentacao {
	ENTRADA("Entrada"),
	SAIDA("Saída");
	
	private String tipo;

	TipoDeMovimentacao(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
