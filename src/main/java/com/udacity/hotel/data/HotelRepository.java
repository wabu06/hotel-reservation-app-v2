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
	
	Map< String, ArrayList<Reservation> > getSingles();
	void updateSingles(Map< String, ArrayList<Reservation> > new_singles);
	
	Map< String, ArrayList<Reservation> > getDoubles();
	void updateDoubles(Map< String, ArrayList<Reservation> > new_doubles);
}
