package br.com.lacerda.estacio.jms;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.joda.time.Instant;

import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.jms.DeliveryMode;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

// Classe que produz mensagens e as envia para uma fila.
public class Produtor {

    // Método principal.
    public static void main(String[] args) throws Exception {
        
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
        QueueSession sessao = conexao.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);

        // Cria objeto que permite enviar mensagens à fila.
        QueueSender produtor = sessao.createSender(fila);
        produtor.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Cria mensagem com a data/hora atual.
        TextMessage mensagem = sessao.createTextMessage(Instant.now().toString());

        // Envia a mensagem.
        produtor.send(mensagem);

        System.out.println("Mensagem enviada: " + mensagem.getText());

        // Fecha a conexão.
        conexao.close();
    }
}
