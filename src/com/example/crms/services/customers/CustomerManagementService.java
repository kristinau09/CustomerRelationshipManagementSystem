package com.example.crms.services.customers;

import java.util.List;

import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

public interface CustomerManagementService 
{
	/**
	 * Takes a customer domain object and saves it in the database
	 */
	public void newCustomer(Customer newCustomer);
	
	/**
	 * The specified customer is updated in the database.
	 */
	public void updateCustomer(Customer changedCustomer);
	
	/**
	 * The specified customer is removed from the database
	 */
	public void deleteCustomer(Customer oldCustomer);
	
	/**
	 * Finds the customer by Id
	 */
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException;

	/**
	 * Finds customers by their name. 
	 */
	public List<Customer> findCustomersByName (String name);

	/**
	 * Returns a complete list of the customers in the system.
	 */
	public List<Customer> getAllCustomers();

	/**
	 * For the specified customer, returns the customer object together
	 * will all calls associated with that customer
	 * @throws CustomerNotFoundException 
	 */
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException;
	
	
	/**
	 * Records a call against a particular customer	 * 
	 */
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;
}
