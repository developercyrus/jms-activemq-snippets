package javaee.jms.activemq.example1;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements MessageListener {
    private String queue = "javaee.jms.activemq.example1";
    
    private Connection connection = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;
    private Session session = null;
    private String msg;
    
    public Consumer(String uri) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(uri);  
        connection = factory.createConnection();
        connection.start();
        
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(queue);

        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }
    
    public String getMessage() {
        return msg;
    }
    
    public void close() throws JMSException {
        session.close();  
        connection.close();
    }

    public void onMessage(Message message) {         
        TextMessage txtMsg = (TextMessage) message;
        try {
            msg = txtMsg.getText();
            System.out.println("received: " + msg);
        } catch (JMSException e) {
            e.printStackTrace();                        
        }                             
    } 
}
