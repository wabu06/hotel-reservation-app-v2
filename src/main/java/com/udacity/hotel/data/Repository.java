package com.udacity.hotel.data;


import java.util.Collection;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;


public interface Repository
{
	Collection<IRoom> updateRoomDB(IRoom room);
	
	Collection<Customer> updateCustomerDB(Customer c);
	
	Collection<Reservation> updateReservationDB();
}
