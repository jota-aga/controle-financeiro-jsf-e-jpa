package enums;

public enum TipoDeMovimentacao {
	ENTRADA("Entrada"),
	SAIDA("Saída");
	
	private String tipoDeMovimentacao;

	TipoDeMovimentacao(String tipoDeMovimentacao) {
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}

	public String getTipoDeMovimentacao() {
		return tipoDeMovimentacao;
	}
}
