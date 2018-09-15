package escritaArquivo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escrevedor {

	private final static String userHomePath = System.getProperty("user.home");
	private final static String filePath = userHomePath + File.separator
			+ "repositorioArquivos" + File.separator;

	private File arquivo;

	public Escrevedor(String cabecalho) {
		System.out.println("diretorio do usuario: " + userHomePath);
		System.out.println("diretorio do arquivo: " + filePath);
		this.arquivo = new File(filePath + "arquivo1.csv");

		if (!arquivo.exists()) {
			try {
				new File(filePath).mkdirs();
				arquivo.createNewFile();
				escreveNoArquivo(cabecalho);
			} catch (IOException e) {
				System.out.println("Ops! Deu merda na hora de criar o arquivo " + arquivo.getPath());
				e.printStackTrace();
			}
		}
	}

	public void escreveNoArquivo(String texto) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.arquivo, true);
			writer.write(texto);
			writer.flush();

		} catch (IOException e) {
			System.out.println("Ops! Deu merda na hora de escrever no arquivo " + arquivo.getPath());
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

}
