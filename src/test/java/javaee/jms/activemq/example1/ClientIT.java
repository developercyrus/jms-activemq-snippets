package javaee.jms.activemq.example1;

import static org.junit.Assert.assertEquals;

import org.apache.activemq.broker.BrokerService;
import org.junit.Ignore;
import org.junit.Test;

public class ClientIT { 
    @Test
    public void test1() throws Exception {
        String uri = "tcp://localhost:61616";
        
        Consumer consumer = new Consumer(uri);
        Producer producer = new Producer(uri);     
        
        producer.send("Hello world by queue by plugin");        
        Thread.sleep(1000);
        String expected = consumer.getMessage();
        
        assertEquals("Hello world by queue by plugin", expected);
        
        producer.close();
        consumer.close();
    }

    @Test
    public void test2() throws Exception {
        String uri = "vm://localhost?broker.persistent=false";
        BrokerService broker = new BrokerService();  
        broker.setPersistent(false);  
        broker.setUseJmx(false);  
        broker.start();  
                
        Consumer consumer = new Consumer(uri);
        Producer producer = new Producer(uri);

        producer.send("Hello world by queue by embedded broker");
        Thread.sleep(1000);
        String expected = consumer.getMessage();
        
        assertEquals("Hello world by queue by embedded broker", expected);
        
        broker.stop();
    }
}
