package com.udacity.hotel.services;


import java.util.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.data.*;


public class CustomerService 
{
	 	Map<String, Customer> CustomerMap;
	 	
	 	PretendDataBaseHotelRepo hotelRepo;
		
		final static CustomerService instance = new CustomerService();
		
		public static CustomerService getInstance() { return instance; }
		
		private CustomerService()
		{
			hotelRepo = PretendDataBaseHotelRepo.getInstance();
			//CustomerMap = new HashMap<String, Customer>();
			CustomerMap = hotelRepo.getCustomers();
		} 
		
	 	public Customer addCustomer(String number, String email, String firstName, String lastName, long pinHash)
		{
			
			Customer C = new Customer(firstName, lastName, number, email, pinHash);
			CustomerMap.put(email, C);
			
			hotelRepo.updateCustomers(CustomerMap);
			
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

