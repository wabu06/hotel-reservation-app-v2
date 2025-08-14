package com.udacity.hotel.resources;


import java.util.*;
//import java.util.regex.*;

import java.util.prefs.Preferences;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;


public class AdminResource
{
		CustomerService CS;
		ReservationService RS;
		
		private static final Preferences pwPrefs = Preferences.userNodeForPackage(AdminResource.class);
		
		final static AdminResource arInstance = new AdminResource();
		
		public static AdminResource getInstance() { return arInstance; }
		
		private AdminResource()
		{
			CS = CustomerService.getInstance();
			RS = ReservationService.getInstance();
		}
		
		public int getPasswordHash() {
			return pwPrefs.getInt("PW_HASH", 0);
		}
		
		public void savePasswordHash(String pw) {
			pwPrefs.putInt("PW_HASH", pw.hashCode());
		}
		
		public Customer getCustomer(String email) { return CS.getCustomer(email); }
		
		public void addRooms(List<IRoom> rooms)
		{
			for(IRoom rm: rooms)
				RS.addRoom(rm);
		}
		
		public void addNewRoom(IRoom room) {
			RS.addRoom(room);
		}
		
		public int getRoomCount() {
			return RS.getRoomCount();
		}
		
		public Collection<Reservation> getReservationsForRoom(String rmNum) {
			return RS.getRoomReservations(rmNum);
		}
		
		public Collection<IRoom> getAllRooms() { return RS.getRooms(); }
		
		public Collection<Customer> getAllCustomers() { return CS.getAllCustomers(); }
		
		public IRoom getRoom(String roomId) { return RS.getARoom(roomId); }
		
		public boolean roomExist(String roomId) { return RS.roomExist(roomId); }
		
		public Collection<Reservation> getAllReservations() { return RS.getAllReservations(); }
		
		//public void displayAllReservations() { RS.printAllReservations(); }
}

