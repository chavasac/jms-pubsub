package com.sachin.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.JMSMessageListenerWrapper;

import com.sachin.jms.domain.Employee;

public class SecurityApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		JMSContext jmsContext = connectionFactory.createContext();
		
		/*
		 * Uncomment below piece of code for normal pub sub messaging tutorial
		 */
		/*
		 * JMSConsumer consumer = jmsContext.createConsumer(topic); Employee employee =
		 * consumer.receive().getBody(Employee.class);
		 * System.out.println("Message Received by Security App : " +
		 * employee.getFirstName() + " " + employee.getLastName());
		 */
		
		/*
		 * Below piece of code is to demonstrate the durable subscription tutorial
		 */
		jmsContext.setClientID("securityApp");
		JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
		consumer.close();
		
		Thread.sleep(10000);
		
		consumer = jmsContext.createDurableConsumer(topic, "subscription1");
		Employee employee =	consumer.receive().getBody(Employee.class);
		System.out.println("Message Received by Security App : " + employee.getFirstName() + " " + employee.getLastName());
		
		consumer.close();
		jmsContext.unsubscribe("subscription1");
	}

}
