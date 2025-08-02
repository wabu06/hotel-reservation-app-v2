package com.udacity.hotel.models;


import java.util.*;
//import java.util.regex.*;


public class Reservation
{
		static int IDcount = 0;
		
		private int ID;
		
		private Customer customer;
		private IRoom room;
		
		private long staylength;
		private Double total;
		
		private Date checkInDate;
		private Date checkOutDate;
	
		public Reservation(Customer C, IRoom R, long SL, Double T, Date cid, Date cod)
		{
			ID = ++IDcount;
			
			customer = C;
			room = R;
			
			staylength = SL;
			total = T;
			
			checkInDate = cid;
			checkOutDate = cod;
		}
		
		public Date getCheckInDate() { return checkInDate; }
		public Date getCheckOutDate() { return checkOutDate; }
	
		@Override 
		public String toString()
		{ 
			String resStr = "Reservation ID: " + ID + "\n" + customer.toString() + "\n" + room.toString() + "\nLength Of Stay: " + staylength + " nights" + "\nTotal Cost Of Stay: $" + total + "\nCheck In Date: ";
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
		
		@Override
		public int hashCode() {
			return Objects.hash(customer, room, checkInDate, checkOutDate);
		}
}
