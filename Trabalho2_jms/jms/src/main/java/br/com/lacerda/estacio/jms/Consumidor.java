package br.com.lacerda.estacio.jms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

//Classe que consome mensagens a partir de uma fila.
public class Consumidor {

	// Método principal.
	public static void main(String args[]) throws Exception {

		// Propriedades para configuração do ambiente de execução.
		Properties ambiente = new Properties();
		ambiente.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		ambiente.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		ambiente.put("queue.filaDeMensagens", "FilaDeMensagens");

		// Contexto inicial para o ambiente de execução.
		InitialContext contexto = new InitialContext(ambiente);

		// Procura pela fila de mensagens.
		Queue fila = (Queue) contexto.lookup("filaDeMensagens");

		// Procura pela "fábrica" de conexões às filas de mensagem.
		QueueConnectionFactory fabricaDeConexoes = (QueueConnectionFactory) contexto.lookup("QueueConnectionFactory");

		// Cria uma conexão à fila.
		QueueConnection conexao = fabricaDeConexoes.createQueueConnection();

		// Cria uma sessão de acesso à fila.
		QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		// Cria objeto que permite rcuperar mensagens a partir da fila.
		QueueReceiver consumidor = sessao.createReceiver(fila);

		// Inicia a conexão.
		conexao.start();
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(280, 500));
		frame.setContentPane(scrollPane);
		frame.setTitle("Fila de operações");
		frame.pack();
		frame.setVisible(true);
		// Recebe uma mensagem.
		while (true) {
			TextMessage mensagem = (TextMessage) consumidor.receive();
			double resultado = 0;
			try {
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				String[] msgDecomposta = decompoeMsg(mensagem.getText());
				double numA = Double.parseDouble(msgDecomposta[0]);
				Operacao operacao = Operacao.getOperacao(msgDecomposta[1]);
				double numB = Double.parseDouble(msgDecomposta[2]);
				resultado = calculaPorOperacao(numA, operacao, numB);

				String resultadoOperacao = String.format("%s %s %s = %f", msgDecomposta[0], operacao.getSimbolo(),
						msgDecomposta[2], resultado);
				String texto = textArea.getText();
				textArea.setText(
						(texto == null || texto.isEmpty() ? resultadoOperacao : texto + "\r\n" + resultadoOperacao));

				Thread.sleep(500);
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Mensagem no formato inválido: " + mensagem.getText(), "Ops!",
						JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Mensagem com texto ao invés de números: " + mensagem.getText(),
						"Ops!", JOptionPane.ERROR_MESSAGE);
			}

		}
		// // Fecha a conexão.
		// conexao.close();
	}

	private static String[] decompoeMsg(String msg) {
		return msg.split("\\|\\|");
	}

	private static double calculaPorOperacao(double numA, Operacao operacao, double numB) {
		switch (operacao) {
		case SOMA:
			return numA + numB;
		case SUBTRACAO:
			return numA - numB;
		case MULTIPLICACAO:
			return numA * numB;
		case DIVISAO:
			return numA / numB;
		default:
			return 0;
		}

	}
}
