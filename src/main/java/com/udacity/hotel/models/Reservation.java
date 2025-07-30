package com.udacity.hotel.models;


import java.util.*;
//import java.util.regex.*;


public class Reservation
{
		private Customer customer;
		private IRoom room;
		private Date checkInDate;
		private Date checkOutDate;
	
		public Reservation(Customer C, IRoom R, Date cid, Date cod)
		{
			customer = C;
			room = R;
			checkInDate = cid;
			checkOutDate = cod;
		}
		
		public Date getCheckInDate() { return checkInDate; }
		public Date getCheckOutDate() { return checkOutDate; }
	
		@Override 
		public String toString()
		{ 
			String resStr = customer.toString() + "\n" + room.toString() + "\nCheck In Date: ";
			return resStr + checkInDate.toString() + "\nCheck Out Date: " + checkOutDate.toString();
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Reservation) )
				return false;
		
			Reservation R = (Reservation) O;
			
			return customer.equals(R.customer) && room.equals(R.room) && checkInDate.equals(R.checkInDate) && checkOutDate.equals(R.checkOutDate);
		}
}
