package com.udacity.hotel.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.*;
import java.util.prefs.Preferences;

//import java.util.stream.Collectors;

//import java.util.function.Function;

import com.udacity.hotel.models.*;

public class PretendDataBaseHotelRepo implements HotelRepository
{
	Map<String, Customer> customers;
	Map< String, ArrayList<Reservation> > singles;
	Map< String, ArrayList<Reservation> > doubles;
	
	final static PretendDataBaseHotelRepo instance = new PretendDataBaseHotelRepo();
	
	public static PretendDataBaseHotelRepo getInstance() { return instance; }
	
		//preference keys
  private static final String CUSTOMERS = "CUSTOMERS"
  private static final String SINGLES = "SINGLES";
  private static final String DOUBLES = "DOUBLES";

  //private static final Preferences repo_prefs = Preferences.userNodeForPackage(PretendDataBaseHotelRepo.class);
  private Preferences repo_prefs;
  private Gson gson; //used to serialize objects into JSON
  
  private PretendDataBaseHotelRepo()
  {
  	repo_prefs = Preferences.userNodeForPackage(PretendDataBaseHotelRepo.class);
  	gson = new Gson();
  	
  	//GsonBuilder builder = new GsonBuilder();
  	//gson = builder.registerTypeAdapter(IRoom.class, new IRoomDeserializer()).create();
  	
  	String customerString = repo_prefs.get(CUSTOMERS, null);
  	
    if(customerString == null)
    	customers = new HashMap<String, Customer>();
    else
    {
    	Type type = new TypeToken< Map<String, Customer> >() {}.getType();
      customers = gson.fromJson(customerString, type);
    }
  	
  	String singlesString = repo_prefs.get(SINGLES, null);
  	
    if(singlesString == null)
    	singles = new HashMap< String, ArrayList<Reservation> >();
    else
    {
    	Type type = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
      singles = gson.fromJson(singlesString, type);
    }
    
    String doublesString = repo_prefs.get(DOUBLES, null);
    
    if(doublesString == null)
    	doubles = new HashMap< String, ArrayList<Reservation> >();
    else
    {
    	Type type = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
      doubles = gson.fromJson(doublesString, type);
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
  Map< String, ArrayList<Reservation> > getSingles() {
  	return singles;
  }
  
  @Override
  Map< String, ArrayList<Reservation> > getDoubles() {
  	return doubles;
  }
  
  @Override
  void updateSingles(Map< String, ArrayList<Reservation> > singles) {
  	repo_prefs.put(SINGLES, gson.toJson(singles))
  }
  
  @Override
  void updateDoubles(Map< String, ArrayList<Reservation> > doubles) {
		repo_prefs.put(DOUBLES, gson.toJson(doubles))  
  }
}

