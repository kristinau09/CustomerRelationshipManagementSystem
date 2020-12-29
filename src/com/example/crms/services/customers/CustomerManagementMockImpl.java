package com.example.crms.services.customers;

import java.util.HashMap;
import java.util.List;

import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	//store the fake data for this class to represent where key is String and value itself is customer
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
		

	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		// TODO Auto-generated method stub

	}

}
