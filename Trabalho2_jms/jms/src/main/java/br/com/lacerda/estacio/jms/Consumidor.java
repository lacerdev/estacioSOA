package br.com.lacerda.estacio.jms;

import java.awt.Dimension;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSession;
import javax.jms.QueueReceiver;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

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

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 500));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setTitle("Fila de operações");
		frame.pack();
		// frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// Recebe uma mensagem.
		while (true) {
			TextMessage mensagem = (TextMessage) consumidor.receive();
			double resultado = 0;
			try {
				String[] msgDecomposta = decompoeMsg(mensagem.getText());
				double numA = Double.parseDouble(msgDecomposta[0]);
				Operacao operacao = Operacao.getOperacao(msgDecomposta[1]);
				double numB = Double.parseDouble(msgDecomposta[2]);
				resultado = calculaPorOperacao(numA, operacao, numB);

				// // Exibe a mensagem recebida.
				// JOptionPane.showMessageDialog(null, resultado, "RESULTADO:",
				// JOptionPane.INFORMATION_MESSAGE);
				JLabel log = new JLabel(String.format("%s %s %s = %f", msgDecomposta[0], operacao.getSimbolo(),
						msgDecomposta[2], resultado));
				panel.add(log);
				frame.setContentPane(panel);
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
