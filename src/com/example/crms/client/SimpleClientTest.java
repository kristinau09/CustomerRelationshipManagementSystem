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
		
		CallHandlingService callService = container.getBean(CallHandlingService.class);
		DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);
		Call newCall = new Call("Larry will call from google");
		Action action1 = new Action("Call by Larry to call him back", new GregorianCalendar(2021,0,0),"abc");
		Action action2 = new Action("Check our sales department", new GregorianCalendar(2021,0,0),"abc");
		
		//create a list of actions where we are passing these two actions
		List<Action> actions = new ArrayList<Action>();
		actions.add(action1);
		actions.add(action2);
		
		//if customer id is wrong
		try {
			callService.recordCall("klajdlkajdl", newCall, actions);
		}catch(CustomerNotFoundException e) {
			System.out.println("That customer does not exist");
		}
		
		System.out.println("Here are the outstanding actions: ");
		Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("abc");
		for(Action nextAction: incompleteActions) {
			System.out.println(nextAction);
		}
		
		container.close();

	}

}
