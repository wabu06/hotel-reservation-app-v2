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
		
		public Customer createACustomer(String number, String  email,  String firstName, String lastName, long pinHash)
			{ return CS.addCustomer(number, email, firstName, lastName, pinHash); }
		
		public Room getRoom(String roomNumber) {
			return RS.getARoom(roomNumber);
		}
		
		public Reservation bookRoom(String email, Room room, Date checkInDate, Date CheckOutDate) {
			return RS.reserveARoom( CS.getCustomer(email), room, checkInDate, CheckOutDate );
		}
		
		public Collection<Reservation> getAllCustomerReservations(String email) {
			return RS.getCustomerReservations(email);
		}
		
		public Optional<Reservation> getReservationByID(Collection<Reservation> reserves, int ID) {
			return RS.getReservationByID(reserves, ID);
		}
		
		//public Collection<Reservation> getCustomerReservations(String customerEmail)
			//{ return RS.getCustomersReservations( CS.getCustomer(customerEmail) ); }
		
		public Room findRoom(Date checkIn, Date checkOut, RoomType type) {
			return RS.findRoom(checkIn, checkOut, type);
		}
			
		public int getRoomCount() {
			return RS.getRoomCount();
		}
		
		public Reservation removeReservation(Reservation reservation) {
			return RS.removeReservation(reservation);
		}
		
		public Reservation restoreReservation(Reservation R) {
			return RS.restoreReservation(R);
		}
		
		public Reservation changeReservation(Reservation R, String rm, Date cid, Date cod) {
			return RS.changeReservation(R, rm, cid, cod);
		}
		
//		public HashMap<String, IRoom> recommendedRooms(Date cid, Date cod)
//		{
//			Calendar cal = Calendar.getInstance();
//						
//			cal.setTime(cid);
//			cal.add(Calendar.DAY_OF_MONTH, 7);
//			cid = cal.getTime();
//						
//			cal.setTime(cod);
//			cal.add(Calendar.DAY_OF_MONTH, 7);
//			cod = cal.getTime();
//			
//			return findARoom(cid, cod);
//		}
		 
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

