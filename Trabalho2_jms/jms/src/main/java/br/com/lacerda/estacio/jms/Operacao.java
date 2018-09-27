package br.com.lacerda.estacio.jms;

public enum Operacao {
	SOMA("+"), SUBTRACAO("-"), MULTIPLICACAO("*"), DIVISAO("/");

	private String simbolo;

	private Operacao(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public static Operacao getOperacao(String operText) {
		for (Operacao operacao : Operacao.values()) {
			if (operacao.name().equals(operText)) {
				return operacao;
			}
		}
		return null;
	}
}