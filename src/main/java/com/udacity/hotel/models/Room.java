package com.udacity.hotel.models;


import java.util.*;
import java.util.Properties;
import java.util.prefs.Preferences;

import java.io.IOException;
import java.io.InputStream;


public class Room
{
	private static Preferences price_prefs;
	
	static Double single_price;
	static Double double_price;
	
	static
	{
		Properties props = new Properties();
		
		try (InputStream is = new Room("555", RoomType.NONE).getClass().getClassLoader().getResourceAsStream("price.properties")) {
       props.load(is);
    }
    	//catch (IOException ioe ) {
    catch (Exception exp ) {
       //price.properties not found
       System.exit(1);
    }
    
    Double single = Double.parseDouble( props.getProperty("single.price") );
    Double _double = Double.parseDouble( props.getProperty("double.price") );
    
    price_prefs = Preferences.userNodeForPackage(Room.class);
    
    single_price = price_prefs.getDouble("SINGLES", single);
    double_price = price_prefs.getDouble("DOUBLES", _double);
	}
	
	private String roomNumber;
	private Double price;
	private RoomType type;
	
	ArrayList<Reservation> reservations;
	
	public Room(String N, RoomType T)
	{
		roomNumber = N;
		type = T;
		
		if(type == RoomType.SINGLE)
			price = single_price;
		else
			price = double_price;
			
		reservations = new ArrayList<Reservation>();
	}
		
	public Room(Room rm)
	{
		roomNumber = rm.roomNumber;
		price = rm.price;
		type = rm.type;
		
		reservations = null;		
	}
	
	public static Double getSinglePrice() {
		return single_price;
	}
	
	public static Double getDoublePrice() {
		return double_price;
	}
	
	public static void setSinglePrice(Double p) {
		single_price = p;
		price_prefs.putDouble("SINGLES", single_price);
	}
	public static void setDoublePrice(Double p) {
		double_price = p;
		price_prefs.putDouble("DOUBLES", double_price);
	}
	
	public Double setRoomPrice()
	{
		if(type == RoomType.SINGLE)
			price = single_price;
		else
			price = double_price;
		
		return price;
	}
	
	public String getRoomNumber() { return roomNumber; }
	public Double getRoomPrice() { return price; }
	public RoomType getRoomType() { return type; }
	
	public ArrayList<Reservation> getReservations() {
		return reservations; 
	}
	
	public void setReservations(ArrayList<Reservation> R) {
		reservations = R;
	}
	
	public void nullifyReservations() {
		reservations = null;
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

