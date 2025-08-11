package com.udacity.hotel.services;


import java.util.*;

import com.udacity.hotel.models.*;


public class CustomerService 
{
	 	Map<String, Customer> CustomerMap;
		
		final static CustomerService instance = new CustomerService();
		
		public static CustomerService getInstance() { return instance; }
		
		private CustomerService() { CustomerMap = new HashMap<String, Customer>(); } 
		
	 	public Customer addCustomer(String number, String email, String firstName, String lastName, long pinHash)
		{
			
			Customer C = new Customer(firstName, lastName, number, email, pinHash);
			CustomerMap.put(email, C);
			return C;
		}
		
		public Customer getCustomer(String customerEmail)
			{ return CustomerMap.get(customerEmail); }
		
//		public Collection<Customer> getAllCustomers()
//		{
//			ArrayList<Customer> customers = new ArrayList<Customer>();
//			
//			for(Customer C: CustomerMap.values() ) { customers.add(C); }
//			
//			return customers;
//		}
		
		public Collection<Customer> getAllCustomers() {
			return CustomerMap.values();
		}
}

