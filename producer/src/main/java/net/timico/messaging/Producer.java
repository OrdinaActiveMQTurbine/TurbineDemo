/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.timico.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 *
 * @author kme20980
 */
public class Producer {
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    
    public Producer(ConnectionFactory factory, String queueName) throws JMSException{
        this.factory = factory;
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        producer = session.createProducer(destination);
    }
    
    public void run() throws JMSException
    {
        for (int i = 0; i < 200; i++)
        {
            //TurbineData tData = new TurbineData(i,"Hallo Koen!!");
            Gson gson = new GsonBuilder().create();
            String jsonMessage = gson.toJson(new TurbineData( "Hallo Koen! ontvang jij dit bericht? " +i));
            System.out.println("Creating Message " + i);
            Message message = session.createTextMessage(jsonMessage);
            //message.setObject(tData);
            
            producer.send(message);
        }
    }
    
     public void close() throws JMSException
    {
        if (connection != null)
        {
            connection.close();
        }
    }
}
