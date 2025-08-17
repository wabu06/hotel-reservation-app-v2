package com.udacity.hotel.data;


import java.util.Collection;
import java.util.Map;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;


public interface HotelRepository
{
	Map<String, Customer> updateCustomers(Map<String, Customer> new_customers);
	Map<String, Customer> getCustomers();
	
	//Collection<IRoom> updateRooms(Collection<IRoom> rooms);
	
	//Collection<Reservation> updateReservations(Collection<Reservation> reservstions);
}
