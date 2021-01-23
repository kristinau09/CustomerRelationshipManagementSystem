package com.example.crms.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;
import com.example.crms.services.callsHandling.CallHandlingService;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerNotFoundException;
import com.example.crms.services.diary.DiaryManagementService;

public class SimpleClientTest {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext container= new ClassPathXmlApplicationContext("application.xml");

		CustomerManagementService customerService = container.getBean(CustomerManagementService.class);		
		CallHandlingService callService = container.getBean(CallHandlingService.class);
		DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);


		Customer newCustomer = new Customer("102", "Google", "Good Customer");
		customerService.newCustomer(newCustomer);


		Call newCall = new Call("Larry Wall called from google");
		Action action1 = new Action("Call back Larry to ask him how things are going", new GregorianCalendar(2021,0,0),"user1234");
		Action action2 = new Action("Check our sales department to make sure Larry is being tracked", new GregorianCalendar(2021,0,0),"user1234");
		

		//create a list of actions
		List<Action> actions = new ArrayList<Action>();
		actions.add(action1);
		actions.add(action2);

		//if customer id is wrong
		try {
			callService.recordCall("102", newCall, actions);
		}catch(CustomerNotFoundException e) {
			System.out.println("That customer does not exist");
		}

		System.out.println("Here are the outstanding actions: ");
		
		Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("user1234");		
		for(Action nextAction: incompleteActions) {
			System.out.println(nextAction);
		}

		container.close();

	}

}
