package com.example.crms.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	//store the fake data for this class to represent. key is String and value itself is customer
	private HashMap<String, Customer> customerMap;
	
	//constructor
	public CustomerManagementMockImpl() {
		customerMap = new HashMap<String, Customer>();
		//crete small number of customer's objects and put it in map
		customerMap.put("100", new Customer("100","Amazon","Notes to amazon"));
		customerMap.put("101", new Customer("101","Google","Notes to google"));
		customerMap.put("102", new Customer("102","Microsoft","Notes to microsoft"));
		customerMap.put("103", new Customer("103","Tesla","Notes to tesla"));
	}

	@Override
	public void newCustomer(Customer newCustomer) {
		customerMap.put(newCustomer.getCustomerId(), newCustomer);

	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		//we will update this later properly. 
		//right out it is doing nothing except putting the same reference back into the map which is already there
		customerMap.put(changedCustomer.getCompanyName(), changedCustomer);
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customerMap.remove(oldCustomer.getCustomerId());
	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		//need to check if customer is existed or not
		Customer foundCustomer = customerMap.get(customerId);
			if(foundCustomer == null) {
				throw new CustomerNotFoundException();
			}
		return foundCustomer;
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		//there is no find method in java api to find particular values in map apart by key
		//for this, create a temp list of customers
		List<Customer> customerList = new ArrayList<Customer>();
		for(Customer customer: customerMap.values()) {
			if(customer.getCompanyName().equals(name)) {
				customerList.add(customer);
			}
		}		
		return customerList;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return new ArrayList<Customer>(customerMap.values());
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		return this.findCustomerById(customerId);
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		Customer customer = this.getFullCustomerDetail(customerId);
		customer.addCall(callDetails);

	}

}
