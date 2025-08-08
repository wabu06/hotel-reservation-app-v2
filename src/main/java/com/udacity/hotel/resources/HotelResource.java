package com.udacity.hotel.resources;


import java.util.*;
//import java.util.regex.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;


public class HotelResource
{
		CustomerService CS;
		ReservationService RS;
		
		final static HotelResource hrInstance = new HotelResource();
		
		public static HotelResource getInstance() { return hrInstance; }
		
		private HotelResource()
		{
			CS = CustomerService.getInstance();
			RS = ReservationService.getInstance();
		}
		
		public String getCustomerEmail(Customer C) { return C.getEmail(); }
		
		public Customer getCustomer(String email) { return CS.getCustomer(email); }
		
		public Customer createACustomer(String  email,  String firstName, String lastName, long pinHash)
			{ return CS.addCustomer(email, firstName, lastName, pinHash); }
		
		public IRoom getRoom(String roomNumber) { return RS.getARoom(roomNumber); }
		
		public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate)
			{ return RS.reserveARoom( CS.getCustomer(customerEmail), room, checkInDate, CheckOutDate ); }
		
		public Collection<Reservation> getAllCustomerReservations(String customerEmail) {
			return RS.getCustomerReservations(customerEmail);
		}
		
		public Optional<Reservation> getReservationByID(String email, int ID) {
			return RS.getReservationByID(email, ID);
		}
		
		public Collection<Reservation> getCustomerReservations(String customerEmail)
			{ return RS.getCustomersReservation( CS.getCustomer(customerEmail) ); }
		
		public HashMap<String, IRoom> findARoom(Date checkIn, Date checkOut)
			{ return RS.findRooms(checkIn, checkOut); }
			
		public int getRoomCount() {
			return RS.getRoomCount();
		}
		
		public HashMap<String, IRoom> recommendedRooms(Date cid, Date cod)
		{
			Calendar cal = Calendar.getInstance();
						
			cal.setTime(cid);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			cid = cal.getTime();
						
			cal.setTime(cod);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			cod = cal.getTime();
			
			return findARoom(cid, cod);
		}
		 
		public Collection<Reservation> getNonCanceledReservations(String email) {
			return RS.getNonCanceledReservations(email);
		}
		
		public Optional<Reservation> cancelReservation(Collection<Reservation> reserves, int ID) {
			return RS.cancelReservation(reserves, ID);
		}
		
		public boolean isAuthentic(String email, String pin)
		{
			Customer C = getCustomer(email);
			
			if(C == null)
				return false;
			
			long pinHash = C.getPinHash();
			
			if( (pinHash != pin.hashCode()) && (!email.equalsIgnoreCase( C.getEmail() )))
				return false;
			else
				return true;
		}
}

