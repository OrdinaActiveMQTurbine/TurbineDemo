package net.timico.messaging;

import com.google.gson.Gson;
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
import org.apache.activemq.broker.BrokerService;

public class App implements MessageListener {

    public static String brokerURL = "tcp://localhost:61616";

    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private static DataManager datamanager;

    public static void main(String[] args) {
        datamanager = new DataManager();
        datamanager.startHibernate();
        
        App app = new App();
        app.run();
    }

    private void run() {
        try {
            BrokerService broker = new BrokerService();
            

            broker.addConnector(brokerURL);
            broker.start();
            
            ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("test");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
            
        } catch (Exception e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }

    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMessage = (TextMessage)message;
//                System.out.println("Bericht ontvangen: " +txtMessage.getText());
                Gson gson = new Gson();
                TurbineData tbd = gson.fromJson(txtMessage.getText(), TurbineData.class);
                System.out.println("ID: " + tbd.getId() +" Stringdata: " +tbd.getStringData() +" Datum: " +tbd.getDatum());
                datamanager.addTurbineData(tbd);

                
                
                
            } else {
                System.out.println("Invalid message received.");
            }
        } catch (JMSException e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    }

}
