package br.com.lacerda.estacio.jms;

public enum Operacao {
	SOMA,SUBTRACAO,MULTIPLICACAO,DIVISAO;
	
	public static Operacao getOperacao(String operText) {
		for (Operacao operacao : Operacao.values()) {
			if(operacao.name().equals(operText)) {
				return operacao;
			}
		}
		return null;
	}
}