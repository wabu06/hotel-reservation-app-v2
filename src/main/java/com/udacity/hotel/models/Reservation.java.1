package com.udacity.hotel.models;


import java.util.*;
//import java.util.regex.*;

import java.util.prefs.Preferences;
import java.text.NumberFormat;


public class Reservation
{
		static int IDcount;
		
		private static final Preferences IDprefs = Preferences.userNodeForPackage(Reservation.class);
		
		private int ID;
		
		private Customer customer;
		private Room room;
		
		private long staylength;
		private Double total;
		
		private Date checkInDate;
		private Date checkOutDate;
		
		private boolean canceled;
	
		public Reservation(Customer C, Room R, long SL, Double T, Date cid, Date cod)
		{
			IDcount = IDprefs.getInt("ID", 0);
				
			ID = ++IDcount;
			
			IDprefs.putInt("ID", IDcount);
			
			customer = C;
			room = R;
			
			staylength = SL;
			total = T;
			
			checkInDate = cid;
			checkOutDate = cod;
			
			canceled = false;
		}
		
		public Customer getCustomer() {
			return customer;
		}
		
		public Room getRoom() {
			return room;
		}

		public long setStayLength(long sl) {
			staylength = sl;
			return staylength;
		}
		
		public Double setTotal(Double t) {
			total = t;
			return total;
		}
		
		public Room setRoom(Room r) {
			room = r;
			return room;
		}
		
		public Date getCheckInDate() {
			return checkInDate;
		}
		
		public Date getCheckOutDate() {
			return checkOutDate;
		}
		
		public Date setCheckInDate(Date cid) {
			checkInDate = cid;
			return checkInDate;
		}
		
		public Date setCheckOutDate(Date cod) {
			checkOutDate = cod;
			return checkOutDate;
		}
		
		public int getID() {
			return ID;
		}
		
		public boolean isCanceled() {
			return canceled;
		}
		
		public boolean cancel() {
			canceled = true;
			return canceled;
		}
		
		public boolean uncancel() {
			canceled = false;
			return canceled;
		}
	
		@Override 
		public String toString()
		{ 
			String resStr = "Reservation ID: " + ID + "\n" + customer.toString() + "\n" + room.toString();
			resStr = resStr + "\nLength Of Stay: " + staylength + " nights" + "\nTotal Cost Of Stay: " + NumberFormat.getCurrencyInstance().format(total);
			
			if(canceled)
				resStr = resStr + "\nCheck In Date: " + checkInDate.toString() + " (CANCELED)" + "\nCheck Out Date: " + checkOutDate.toString() + " (CANCELED)";
			else
				resStr = resStr + "\nCheck In Date: " + checkInDate.toString() + "\nCheck Out Date: " + checkOutDate.toString();
				
			return resStr;
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Reservation) )
				return false;
		
			Reservation R = (Reservation) O;
			
			return customer.equals(R.customer) && room.equals(R.room) && checkInDate.equals(R.checkInDate) && checkOutDate.equals(R.checkOutDate) && (ID == R.ID) && (staylength == R.staylength) && total.equals(R.total) && (canceled == R.canceled);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(ID), customer, room, checkInDate, checkOutDate, Long.valueOf(staylength), total, Boolean.valueOf(canceled));
		}
}
