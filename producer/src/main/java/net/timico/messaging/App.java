package net.timico.messaging;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String brokerURL = "tcp://172.19.4.157:61616";
    public static void main( String[] args ) throws Exception
    {
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        
        Producer producer = new Producer(factory, "test");
        producer.run();
        producer.close();
    }
}
