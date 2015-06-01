package javaee.jms.activemq.example2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private String topic = "javaee.jms.activemq.example1";

    private Connection connection = null;
    private Destination destination = null;
    private MessageProducer producer = null;
    private Session session = null;
    
    public Producer(String uri) throws JMSException {        
        ConnectionFactory factory = new ActiveMQConnectionFactory(uri);  
        connection = factory.createConnection();
        connection.start();
        boolean isTransacted = false;

        session = connection.createSession(isTransacted, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(topic);
        
        producer = session.createProducer(destination);
    }
    
    public void send(String msg) throws JMSException {
        TextMessage message = session.createTextMessage(msg);       
        producer.send(message); 
        System.out.println("sent: " + msg);
        
    }
    
    public void close() throws JMSException {
    	session.commit();  
        session.close();  
        connection.close();
    }
}
