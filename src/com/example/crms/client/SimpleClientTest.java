package com.example.crms.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.crms.dao.RecordNotFoundException;
import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;
import com.example.crms.services.callsHandling.CallHandlingService;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerManagementServiceProductionImpl;
import com.example.crms.services.customers.CustomerNotFoundException;
import com.example.crms.services.diary.DiaryManagementService;

public class SimpleClientTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext container= new ClassPathXmlApplicationContext("application.xml");

		CustomerManagementService customerService = container.getBean(CustomerManagementService.class);		
		CallHandlingService callService = container.getBean(CallHandlingService.class);
		DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

		try {
			Customer foundCustomer = customerService.findCustomerById("101");
			foundCustomer.setTelephone("4122340987");
			foundCustomer.setEmail("larryWall@google.com");
			customerService.updateCustomer(foundCustomer);
		}catch(CustomerNotFoundException e) {
			System.out.println("Sorry, customer with that id couldn't be found!");
		}
		
		Call newCall = new Call("Larry Wall called from google");
		Action action1 = new Action("Call back Larry to ask him how things are going", new GregorianCalendar(2021,0,0),"user123");
		Action action2 = new Action("Check our sales department to make sure Larry is being tracked", new GregorianCalendar(2021,0,0),"user123");

		//create a list of actions where we are passing these two actions
		List<Action> actions = new ArrayList<Action>();
		actions.add(action1);
		actions.add(action2);

		//if customer id is wrong
		try {
			callService.recordCall("101", newCall, actions);
		}catch(CustomerNotFoundException e) {
			System.out.println("That customer does not exist");
		}

		System.out.println("Here are the outstanding actions: ");
		Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("user123");
		for(Action nextAction: incompleteActions) {
			System.out.println(nextAction);
		}

		container.close();

	}

}
