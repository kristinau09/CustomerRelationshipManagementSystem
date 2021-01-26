package com.example.crms.services.callsHandling;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerNotFoundException;
import com.example.crms.services.diary.DiaryManagementService;

@Transactional
public class CallHandlingServiceImpl implements CallHandlingService {
	
	//dependencies
	private CustomerManagementService customerService;
    private DiaryManagementService diaryService;
    
    //injecting dependencies through constructor
    public CallHandlingServiceImpl(CustomerManagementService customerService, DiaryManagementService diaryService) {
    	this.customerService=customerService;
    	this.diaryService=diaryService;
		
	}
	@Override
	public void recordCall(String customerId, Call newCall, Collection<Action> actions)	throws CustomerNotFoundException {
		//1. call the customer service to record the call
		customerService.recordCall(customerId, newCall);
		
		//2.call the diary service to record the actions
		//recordAction() takes singular action and since we have been passed a collection of actions, we will loop through
		for(Action nextAction:actions) {
			diaryService.recordAction(nextAction);
		}
		
	}

}
