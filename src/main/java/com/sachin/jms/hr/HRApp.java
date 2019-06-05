package com.sachin.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.sachin.jms.domain.Employee;

public class HRApp {

	public static void main(String[] args) throws NamingException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		JMSContext jmsContext = connectionFactory.createContext();
		
		Employee employee = new Employee();
		employee.setId(123);
		employee.setFirstName("Sachin");
		employee.setLastName("Chavan");
		employee.setEmail("abc@gmail.com");
		employee.setDesignation("Software Developer");
		employee.setPhone("12333444");
		
		jmsContext.createProducer().send(topic, employee);
		System.out.println("Message sent");

	}

}
