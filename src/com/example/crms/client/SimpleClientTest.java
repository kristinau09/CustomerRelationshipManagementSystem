package com.example.crms.client;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.crms.domain.Customer;
import com.example.crms.services.customers.CustomerManagementService;

public class SimpleClientTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext container= new ClassPathXmlApplicationContext("application.xml");
		
		CustomerManagementService customerService = container.getBean("customerService", CustomerManagementService.class);
		//store the result in list
		List<Customer> allCustomers = customerService.getAllCustomers();
		for(Customer next: allCustomers) {
			System.out.println(next);
		}
		
		container.close();

	}

}
