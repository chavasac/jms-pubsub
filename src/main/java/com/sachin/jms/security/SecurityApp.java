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

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		JMSContext jmsContext = connectionFactory.createContext();
		
		JMSConsumer consumer = jmsContext.createConsumer(topic);
		Employee employee = consumer.receive().getBody(Employee.class);
		System.out.println("Message Received by Security App : " + employee.getFirstName() + " " + employee.getLastName());

	}

}
