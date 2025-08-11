package com.udacity.hotel.models;


import java.util.*;
//import java.util.regex.*;


public class Customer
{
		private String firstName;
		private String lastName;
		
		private String phone;
		private String email;
		
		private long pinHash;
	
		public Customer(String F, String L, String N, String E, long H)
		{
			phone = N;
			email = E;
			
			firstName = F;
			lastName = L;
			
			pinHash = H;
		}
		
		public long getPinHash() {
			return pinHash;
		}
		
		public String getEmail() {
			return email;
		}
	
		@Override 
		public String toString() { return "Customer Name: " + firstName + " " + lastName + "\nPhone Number: " + phone + "\nCustomer Email: " + email; }
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Customer) )
				return false;
		
			Customer C = (Customer) O;
			
			return firstName.equals(C.firstName) && lastName.equals(C.lastName) &&  email.equals(C.email) && (pinHash == C.pinHash);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(firstName, lastName, email); }
}

