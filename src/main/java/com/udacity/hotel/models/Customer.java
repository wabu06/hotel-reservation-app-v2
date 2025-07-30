package com.udacity.hotel.models;


import java.util.*;
import java.util.regex.*;


public class Customer
{
		private String firstName;
		private String lastName;
		private String email;
	
		public Customer(String F, String L, String E)
		{
			String eRegX = "^(\\w+)@(\\w+)\\.(\\w+)$";
			Pattern pattern = Pattern.compile(eRegX);
			boolean match = pattern.matcher(E).matches();
		
			if (!match)
			{
				eRegX = "^(\\w+)@(\\w+)\\.(\\w+)\\.(\\w+)$";
				pattern = Pattern.compile(eRegX);
				match = pattern.matcher(E).matches();
				
				if(match)
				{
					email = E;
					firstName = F;
					lastName = L;
				}
				else
					throw new IllegalArgumentException("Invalid Email Format!!");
			}
			else
			{
				email = E;
				firstName = F;
				lastName = L;
			}
		}
		
		public String getEmail() { return email; }
	
		@Override 
		public String toString() { return "Customer Name: " + firstName + " " + lastName + "\nCustomer Email: " + email; }
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Customer) )
				return false;
		
			Customer C = (Customer) O;
			
			return firstName.equals(C.firstName) && lastName.equals(C.lastName) &&  email.equals(C.email);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(firstName, lastName, email); }
}

