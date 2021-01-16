package com.example.crms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
	
	private static final String INSERT__CALL_SQL = "INSERT INTO CALL_TABLE(NOTES, TIME_AND_DATE, CUSTOMER_ID) VALUES(?,?,?)";
	private static final String SELECT_ALL_CUSTOMERS_SQL = "SELECT * FROM CUSTOMER";
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE CUSTOMER SET COMPANY_NAME=?, EMAIL=?, TELEPHONE=?, NOTES=? WHERE CUSTOMER_ID=?";
	private static final String SELECT_CUSTOMER_BY_COMPANY_NAME_SQL = "SELECT * FROM CUSTOMER WHERE COMPANY_NAME = ?";
	private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
	private static final String INSERT_CUSTOMER_SQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, COMPANY_NAME, EMAIL, TELEPHONE, NOTES) VALUES (?,?,?,?,?)";
	private static final String CREATE_CALL_TABLE_SQL = "CREATE TABLE CALL_TABLE(NOTES VARCHAR(255),\r\n" + 
			                                            "TIME_AND_DATE DATE, CUSTOMER_ID VARCHAR(20))";
	private static final String CREATE_CUSTOMER_TABLE_SQL = "CREATE TABLE CUSTOMER(CUSTOMER_ID VARCHAR(20),\r\n" + 
		                                                    "COMPANY_NAME VARCHAR(50), EMAIL VARCHAR(50), TELEPHONE\r\n" + 
		                                                    "VARCHAR(20), NOTES VARCHAR(255))";
	private JdbcTemplate template;
	
	//injecting template through constructor
	public CustomerDaoJdbcTemplateImpl(JdbcTemplate template) {
		this.template=template;
	}
	
	public void createTables() {
		//creating two tables: customer and call since they are closely related to each other, we can simply combine them in single dao
		try {
			template.update(CREATE_CUSTOMER_TABLE_SQL);
		}catch(BadSqlGrammarException e) {
			System.out.println("Assuming the Customer table already exists.");
		}
		
		try {
			template.update(CREATE_CALL_TABLE_SQL);
		}catch(BadSqlGrammarException e) {
			System.out.println("Assuming the Call table already exists.");
		}
	}
	/**
	 * Creates a new customer record in the database
	 */
	@Override
	public void create(Customer customer) {
		template.update(INSERT_CUSTOMER_SQL,
				customer.getCustomerId(), customer.getCompanyName(), customer.getEmail(), 
				customer.getTelephone(), customer.getNotes());

	}
	/**
	 * Finds a customer based on their ID
	 */
	@Override
	public Customer getById(String customerId) throws RecordNotFoundException {
		//if the query does not return exactly one row, or does not return exactly one column in that row
		try {
			return template.queryForObject(SELECT_CUSTOMER_BY_ID_SQL, new CustomerRowMapper(), customerId);
			
		}catch(IncorrectResultSizeDataAccessException i) {
			throw new RecordNotFoundException();
		}
		
	}
	/**
	 * Finds all customers whose company name matches the specified name
	 */
	@Override
	public List<Customer> getByName(String companyName) {
		return template.query(SELECT_CUSTOMER_BY_COMPANY_NAME_SQL, new CustomerRowMapper(), companyName);
		
	}
	/**
	 * Updates the specified customer in the database.
	 */
	@Override
	public void update(Customer customerToUpdate) throws RecordNotFoundException {
		//rowsUpdated reflects the number of rows updated
		int rowsUpdated = template.update(UPDATE_CUSTOMER_SQL,
				          customerToUpdate.getCompanyName(), 
				          customerToUpdate.getEmail(), 
				          customerToUpdate.getTelephone(), 
				          customerToUpdate.getNotes(), 
				          customerToUpdate.getCustomerId());
		//rows updated must be 1 otherwise throw an exception
		if(rowsUpdated != 1) {
			throw new RecordNotFoundException();
		}

	}
	/**
	 * Deletes the specified customer from the database.
	 */
	@Override
	public void delete(Customer oldCustomer) throws RecordNotFoundException {
		int rowsDeleted = template.update("DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?", oldCustomer.getCustomerId());
		//rowsDeleted must be 1 otherwise throw an exception 
		if(rowsDeleted != 1 ) {
			throw new RecordNotFoundException();
		}

	}
	/**
	 * Returns a complete collection of customer objects. Note that it is NOT necessary
	 * to for this method to also return the associated calls (ie getCalls() will return null). 
	 * 
	 * This is for efficiency reasons - we may not be interested in the calls for ALL customers
	 * in the system.
	 */
	@Override
	public List<Customer> getAllCustomers() {
		
		return template.query(SELECT_ALL_CUSTOMERS_SQL, new CustomerRowMapper());
	}
	/**
	 * Returns the full detail for this customer - ie the customer object and ALL
	 * calls associated with this customer
	 */
	@Override
	public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
		//find the customer by id
		Customer customer = this.getById(customerId);
		
		List<Call> allCallsForTheCustomer = template.query("SELECT * FROM CALL_TABLE WHERE CUSTOMER_ID=?", new CallRowMapper(), customerId);
		customer.setCalls(allCallsForTheCustomer);
		return customer;
	}
	/**
	 * Links the specified call to the customer in the database.
	 */
	@Override
	public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
		//need to verify if customer id exists on call table (customer id is foreign key to call  table) for database integrity
		Customer foundCustomer = this.getById(customerId);		
		template.update(INSERT__CALL_SQL, newCall.getNotes(),newCall.getTimeAndDate(), customerId);

	}

}
class CustomerRowMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet result, int rowNumber) throws SQLException {
		//extract the values
		String customerId = result.getString("CUSTOMER_ID");
		String companyName = result.getString("COMPANY_NAME");
		String email = result.getString("EMAIL");
		String telephone = result.getString("TELEPHONE");
		String notes = result.getString("NOTES");
		//creating a new customer object based on result
		return new Customer(customerId, companyName, email, telephone, notes);
		 
	}
	
}
class CallRowMapper implements RowMapper<Call>{

	@Override
	public Call mapRow(ResultSet result, int rowNumber) throws SQLException {
		//extract the values		
		String notes = result.getString("NOTES");
		Date dateAndTime = result.getDate("TIME_AND_DATE");
		//creating a new call object based on result
		return new Call(notes, dateAndTime);
		 
	}
	
}


