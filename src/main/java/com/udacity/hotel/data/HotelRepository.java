package com.udacity.hotel.data;


import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;

import com.udacity.hotel.models.*;
//import com.udacity.hotel.services.*;


public interface HotelRepository
{
	Map<String, Customer> updateCustomers(Map<String, Customer> new_customers);
	Map<String, Customer> getCustomers();
	
	Map<String, Room> getRooms();
	void updateRooms(Map<String, Room> new_rooms);
	
	void updateReservations(Room room);
}
