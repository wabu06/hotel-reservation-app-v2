package com.udacity.hotel.models;


import java.util.*;


public interface IRoom
{
	public String getRoomNumber();
	public Double getRoomPrice();
	public RoomType getRoomType();
	public boolean isFree();
	
	public Double setRoomPrice(Double p);
		
	public ArrayList<Reservation> getReservations();
	public void addReservation(Reservation R);
	public boolean hasReservations();
	public int totalReservations();
}
