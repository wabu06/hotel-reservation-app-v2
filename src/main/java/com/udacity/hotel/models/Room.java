package com.udacity.hotel.models;


import java.util.*;


public class Room
{
	static Double single_price;
	static Double double_price;
	
	private String roomNumber;
	private Double price;
	private RoomType type;
	
	public Room(String N, RoomType T)
	{
		roomNumber = N;
		type = T;
		
		if(type == RoomType.SINGLE)
			price = single_price;
		else
			price = double_price;
	}
		
	public Room(Room rm)
	{
		roomNumber = rm.roomNumber;
		price = rm.price;
		type = rm.type;		
	}
	
	public Double setRoomPrice()
	{
		if(type == RoomType.SINGLE)
			price = single_price;
		else
			price = double_price;
	}
	
	public String getRoomNumber() { return roomNumber; }
	public Double getRoomPrice() { return price; }
	public RoomType getRoomType() { return type; }
	
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

