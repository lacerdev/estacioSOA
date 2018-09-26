package br.com.lacerda.estacio.jms;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

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
        ambiente.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        ambiente.put(Context.PROVIDER_URL, "tcp://localhost:61616");
        ambiente.put("queue.filaDeMensagens", "FilaDeMensagens");

        // Contexto inicial para o ambiente de execução.
        InitialContext contexto = new InitialContext(ambiente);

        // Procura pela fila de mensagens.
        Queue fila = (Queue)contexto.lookup("filaDeMensagens");

        // Procura pela "fábrica" de conexões às filas de mensagem.
        QueueConnectionFactory fabricaDeConexoes = (QueueConnectionFactory)contexto.lookup("QueueConnectionFactory");

        // Cria uma conexão à fila.
        QueueConnection conexao = fabricaDeConexoes.createQueueConnection();

        // Cria uma sessão de acesso à fila.
        QueueSession sessao = conexao.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

        // Cria objeto que permite rcuperar mensagens a partir da fila.
        QueueReceiver consumidor = sessao.createReceiver(fila);

        // Inicia a conexão.
        conexao.start();

        // Recebe uma mensagem.
        TextMessage mensagem = (TextMessage)consumidor.receive();

        // Exibe a mensagem recebida.
        System.out.println("Mensagem recebida: " + mensagem.getText());

        // Fecha a conexão.
        conexao.close();
    }
}
