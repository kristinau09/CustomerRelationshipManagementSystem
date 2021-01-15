package com.example.crms.services.callsHandling;

import java.util.Collection;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.services.customers.CustomerNotFoundException;

public interface CallHandlingService 
{
	/**
	 * Records a call with the customer management service, and also records
	 * any actions in the diary service
	 */
	public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException;
}
