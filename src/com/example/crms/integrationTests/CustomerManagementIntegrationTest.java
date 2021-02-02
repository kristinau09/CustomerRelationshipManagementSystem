package com.example.crms.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;
import com.example.crms.services.callsHandling.CallHandlingService;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/datasource-test.xml")
@Transactional
public class CustomerManagementIntegrationTest {
	
	@Autowired
	private CustomerManagementService customerService;
	
	@Autowired
	private CallHandlingService callsService;
	//creating a customer record
	@Test
	public void testCreatingCustomerRecord() {
		
		//arrange
		Customer testCustomer = new Customer("9191", "Sara", "email", "telephone", "notes");
		
		//act
		customerService.newCustomer(testCustomer);
		
		List<Customer> customerList = customerService.getAllCustomers();
		int numberOfCustomers = customerList.size();
		
		//assert
		assertEquals(1, numberOfCustomers);		
	}
	
	@Test
	public void testFindingCustomer() {
		
		//arrange
		Customer testCustomer = new Customer("9191", "Sara", "email", "telephone", "notes");
		customerService.newCustomer(testCustomer);
		
		//act
		try {
			Customer foundCustomer = customerService.findCustomerById("9191");
			//making sure that the customer that was found matches the customer that we originally sent in
			assertEquals(testCustomer, foundCustomer);
			
		} catch (CustomerNotFoundException e) {
			fail("Customer not found");
		}
	 }
	
	@Test
	public void testAddingCallToACustomer() {
		
		//arrange
		Customer testCustomer = new Customer("9191", "Sara", "email", "telephone", "notes");
		customerService.newCustomer(testCustomer);
		
		Call testCall = new Call("Just a test call");
		List<Action> listOfActions = new ArrayList<Action>();
		
		
		try {
			callsService.recordCall("9191", testCall, listOfActions);
			
			Customer foundCustomer = customerService.getFullCustomerDetail("9191");
			//get the calls from found customer and get first element from the list which is going to be a call
			Call foundCall = foundCustomer.getCalls().get(0);
			//value will be the same as the original test call that passed in and the actual value was found call
			assertEquals(testCall, foundCall);
			
		} catch (CustomerNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
	 }
	

}
