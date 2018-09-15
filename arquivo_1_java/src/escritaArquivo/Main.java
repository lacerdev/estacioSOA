package escritaArquivo;

import java.util.Scanner;

public class Main {
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		Escrevedor escrevedor = new Escrevedor("nome;idade;sexo");
		System.out.println("\nBem vindo ao cadastro de Pessoas!");
		String opcao = null;
		while (!"sair".equalsIgnoreCase(opcao)) {
			menuInicial();
			opcao = scan.nextLine();
			if ("1".trim().equals(opcao)) {
				System.out.print("Insira o nome da pessoa: ");
				String nome = scan.nextLine();
				System.out.print("Insira o sexo da pessoa (M/F): ");
				char sexo = scan.nextLine().toCharArray()[0];
				System.out.print("Insira a idade da pessoa: ");
				int idade = Integer.parseInt(scan.nextLine());
				escrevedor.escreveNoArquivo(String.format("\r\n%s;%d;%s", nome, idade, sexo));
				System.out.print(
						String.format("\nPessoa cadastrada com sucesso! %s, %d anos, sexo: %s\n", nome, idade, sexo));
			}
		}

		System.out.println("Programa finalizado.");
		System.out.println("Seu arquivo de cadastro está disponível em: " + escrevedor.getArquivo().getPath());
	}

	private static void menuInicial() {
		System.out.println("\n---------------------------------");
		System.out.println("Para cadastrar uma nova pessoa digite 1");
		System.out.println("Para encerrar digite 'sair'");
		System.out.println("---------------------------------\n");
	}

}
