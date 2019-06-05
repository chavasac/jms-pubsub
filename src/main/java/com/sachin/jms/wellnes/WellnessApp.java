package com.sachin.jms.wellnes;

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

public class WellnessApp {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		JMSContext jmsContext = connectionFactory.createContext();

		/*
		 * Uncomment below piece of code for normal pub sub tutorial and comment
		 * remaining code below
		 */
		/*
		 * JMSConsumer consumer = jmsContext.createConsumer(topic); Employee employee =
		 * consumer.receive().getBody(Employee.class);
		 * System.out.println("Message Received by Wellness App : " +
		 * employee.getFirstName() + " " + employee.getLastName());
		 */

		/*
		 * Below piece of code demonstrates for shared subscription is implemented in
		 * jms consumer
		 */

		JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
		JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

		for (int i = 0; i < 10; i++) {
			Employee employee1 = consumer1.receive().getBody(Employee.class);
			System.out.println(
					"Message Received by Consumer 1 : " + employee1.getFirstName() + " " + employee1.getLastName());

			Employee employee2 = consumer2.receive().getBody(Employee.class);
			System.out.println(
					"Message Received by Consumer 2 : " + employee2.getFirstName() + " " + employee2.getLastName());
		}

	}

}
