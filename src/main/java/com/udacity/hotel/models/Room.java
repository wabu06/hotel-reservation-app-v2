package com.udacity.hotel.models;


import java.util.*;


public class Room implements IRoom
{
		/* protected */ String roomNumber;
		/* protected */ Double price;
		/* protected */ RoomType type;
		
		ArrayList<Reservation> RoomReservations; // Reservations for the room
	
		public Room(String N, Double P, RoomType T)
		{
			roomNumber = N;
			price = P;
			type = T;
			
			RoomReservations = new ArrayList<Reservation>();
		}
		
		public Room(Room rm)
		{
			roomNumber = rm.roomNumber;
			price = rm.price;
			type = rm.type;
			
			RoomReservations = null;
		}
	
		public String getRoomNumber() { return roomNumber; }
		public Double getRoomPrice() { return price; }
		public RoomType getRoomType() { return type; }
		
		public Double setRoomPrice(Double p) {
			price = p;
			return price;
		}
		
		public ArrayList<Reservation> getReservations() {
			return RoomReservations;
		}
		
		public ArrayList<Reservation> setReservations(ArrayList<Reservation> reserves) {
			RoomReservations = reserves;
			return RoomReservations;
		}
		
		public void addReservation(Reservation R) { RoomReservations.add(R); }
		public boolean hasReservations() { return !RoomReservations.isEmpty(); }
		public int totalReservations() { return RoomReservations.size(); }
	
		public boolean isFree()
		{
			if (price == 0.0)
				return true;
			else
				return false;
		}
	
		@Override 
		public String toString()
		{
			String RmStr = "Room Number: " + roomNumber + "\nPer Night Price: $" + price + "\nRoom Type: ";
		
			if (type ==  RoomType.SINGLE)
				RmStr += "SINGLE";
			else
				RmStr += "DOUBLE";
		
			return RmStr;
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Room) )
				return false;
		
			Room R = (Room) O;
			
			return roomNumber.equals(R.roomNumber) && price.equals(R.price) && (type == R.type); //&& RoomReservations.equals(R.RoomReservations);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(roomNumber, price, type);
		}
}

