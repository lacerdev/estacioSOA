package escritaArquivo;

import java.util.Scanner;

public class Main {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {

		Escrevedor escrevedor = new Escrevedor("nome;idade;sexo");
		String opcao = menuInicial();
		if ("sair".equalsIgnoreCase(opcao)) {
			System.out.println("Programa finalizado.");
			System.out.println("Seu arquivo de cadastro está disponível em: " + escrevedor.getArquivo().getPath());
		} else if ("1".trim().equals(opcao)) {
			System.out.print("Insira o nome da pessoa: ");
			String nome = scan.nextLine();
			System.out.print("Insira o sexo da pessoa (M/F): ");
			char sexo = scan.nextLine().toCharArray()[0];
			System.out.print("Insira a idade da pessoa: ");
			int idade = scan.nextInt();
			Pessoa pessoa = new Pessoa(nome, idade, sexo);
			escrevedor.escreveNoArquivo(String.format("\r\n%s;%d;%s", nome, idade, sexo));
		} else {
			System.out.println("Opção inválida!");
		}
	}
	
	private static String menuInicial() {
		System.out.println("Bem vindo ao cadastro de Pessoas!");
		System.out.println("---------------------------------");
		System.out.println("Para cadastrar uma nova pessoa digite 1");
		System.out.println("Para encerrar digite 'sair'");
		System.out.println("---------------------------------");
		return scan.nextLine();
	}

}
