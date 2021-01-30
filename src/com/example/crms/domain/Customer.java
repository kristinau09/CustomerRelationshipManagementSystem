package com.example.crms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents a customer in the Customer Relationship Management System (CRMS).
 */
@Entity
public class Customer 
{
	/**
	 * A simple unique value for the customer - note this is determined
	 * by the business and is not necessarily the database primary key.
	 */
	@Id
	private String customerId;
	private String companyName;
	private String email;
	private String telephone;
	private String notes;
	
	/**
	 * References to any Calls this customer has made
	 */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Call> calls;	
	
	// needed for JPA
	public Customer() {}
	
	/**
	 * Constructor - id, notes and name are required
	 */
	public Customer(String customerId, String companyName, String email,
			        String telephone, String notes)
	{
		this(customerId, companyName, notes);
		this.email = email;
		this.telephone = telephone;
	}
	
	/**
	 * Constructor - email and telephone are optional
	 */
	public Customer(String customerId, String companyName, String notes)
	{
		this.customerId = customerId;
		this.companyName = companyName;
		this.notes = notes;
		this.calls = new ArrayList<Call>();
	}
	
	/**
	 * Add a new call for this customer
	 */
	public void addCall(Call callDetails) 
	{
		this.calls.add(callDetails);		
	}
	
	public String toString()
	{
		return this.customerId + ": " + this.companyName ;
	}

	public String getCustomerId() 
	{
		return this.customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getNotes() {
		return notes;
	}

	public List<Call> getCalls() {
		return calls;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}

	
}
