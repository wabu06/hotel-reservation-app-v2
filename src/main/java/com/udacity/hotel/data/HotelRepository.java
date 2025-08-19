package com.udacity.hotel.data;


import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;


public interface HotelRepository
{
	Map<String, Customer> updateCustomers(Map<String, Customer> new_customers);
	Map<String, Customer> getCustomers();
	
	Collection<IRoom> updateRooms(Collection<IRoom> new_rooms);
	Map<String, IRoom> getRooms();
	
	Map< String, ArrayList<Reservation> > updateReservations(Map< String, ArrayList<Reservation> > reservations);
	Map< String, ArrayList<Reservation> > getReservations();
}
