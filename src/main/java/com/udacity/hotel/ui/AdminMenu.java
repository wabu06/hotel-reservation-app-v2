package com.udacity.hotel.ui;

import java.util.Collection;
import com.udacity.hotel.models.IRoom;


public interface AdminMenu
{
	void adminMenuManager();
	void displayAllCustomers();
	void displayAllRooms();
	void changePriceForSingles();
	void changePriceForDoubles();
	void displayAllReservations();
}

