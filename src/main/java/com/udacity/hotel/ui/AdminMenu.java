package com.udacity.hotel.ui;

import java.util.Collection;
import com.udacity.hotel.models.IRoom;


public interface AdminMenu
{
	void adminMenuManager();
	void displayAllCustomers();
	Collection<IRoom> displayAllRooms();
	void changeRoomPrice();
	void displayAllReservations();
	void addRooms();
}

