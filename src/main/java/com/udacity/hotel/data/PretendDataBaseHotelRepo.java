package com.udacity.hotel.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.*;
import java.util.prefs.Preferences;

import java.util.stream.Collectors;

import java.util.function.Function;

import com.udacity.hotel.models.*;

public class PretendDataBaseHotelRepo implements HotelRepository
{
	Map<String, Customer> customers;
	List<IRoom> rooms;
	Map< String, ArrayList<Reservation> > reservations;
	
	final static PretendDataBaseHotelRepo instance = new PretendDataBaseHotelRepo();
	
	public static PretendDataBaseHotelRepo getInstance() { return instance; }
	
		//preference keys
  private static final String CUSTOMERS = "CUSTOMERS_TEST";
  private static final String ROOMS = "ROOMS_TEST";
  private static final String RESERVATIONS = "RESERVATIONS_TEST";

  //private static final Preferences repo_prefs = Preferences.userNodeForPackage(PretendDataBaseHotelRepo.class);
  private Preferences repo_prefs;
  private Gson gson; //used to serialize objects into JSON
  
  private PretendDataBaseHotelRepo()
  {
  	repo_prefs = Preferences.userNodeForPackage(PretendDataBaseHotelRepo.class);
  	gson = new Gson();
  	
  	String customerString = repo_prefs.get(CUSTOMERS, null);
  	
    if(customerString == null)
    	customers = new HashMap<String, Customer>();
    else
    {
    	Type type = new TypeToken< Map<String, Customer> >() {}.getType();
      customers = gson.fromJson(customerString, type);
    }
    
    String roomString = repo_prefs.get(ROOMS, null);
    
    if(roomString == null)
    	rooms = new ArrayList<IRoom>();
    else
    {
    	Type type = new TypeToken< List<IRoom> >() {}.getType();
      rooms = gson.fromJson(roomString, type);
    }
    
    String reservationString = repo_prefs.get(RESERVATIONS, null);
    
    if(reservationString == null)
    	reservations = new HashMap< String, ArrayList<Reservation> > ();
    else
    {
    	Type type = new TypeToken< Map< String, ArrayList<Reservation> >  >() {}.getType();
      reservations = gson.fromJson(reservationString, type);
    }
  }
  
  @Override
  public Map<String, Customer> updateCustomers(Map<String, Customer> new_customers)
  {
  	repo_prefs.put(CUSTOMERS, gson.toJson(new_customers));
  	return new_customers;
  }
  
  @Override
  public Map<String, Customer> getCustomers() {
 			return customers; 	
  }
  
  @Override
  public Collection<IRoom> updateRooms(Collection<IRoom> new_rooms)
  {
  	ArrayList<IRoom> db_rooms = new ArrayList<>();
  	
  	for(IRoom rm: new_rooms)
  	{
  		if(rm instanceof Room)
  			db_rooms.add( new Room( (Room) rm));
  		else
  			db_rooms.add( new FreeRoom( (FreeRoom) rm));
  	}
  	
  	repo_prefs.put(ROOMS, gson.toJson(db_rooms));
  	
  	return new_rooms;
  }
  
  @Override
  public Map<String, IRoom> getRooms()
  {
  	if(rooms.isEmpty())
  		return new HashMap<String, IRoom>();
  	else
  	{
  		for(IRoom rm: rooms)
  		{
  			ArrayList<Reservation> reserves = reservations.values().stream().flatMap(res -> res.stream())
  																														 .filter(res -> res.getRoom().getRoomNumber().compareTo( rm.getRoomNumber() ) == 0)
  																														 .collect(Collectors.toCollection(ArrayList::new));
  			
  			rm.setReservations(reserves);
  		}
  		return rooms.stream().collect(Collectors.toMap(r -> r.getRoomNumber(), Function.identity()));
  	}
  }
  
  @Override
	public Map< String, ArrayList<Reservation> > updateReservations(Map< String, ArrayList<Reservation> > reserves)
	{
		repo_prefs.put(RESERVATIONS, gson.toJson(reserves));
		return reserves;
	}
	
	@Override
	public Map< String, ArrayList<Reservation> > getReservations() {
		return reservations;
	}
}



