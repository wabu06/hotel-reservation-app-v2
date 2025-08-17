package com.udacity.hotel.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;
import java.util.prefs.Preferences;

import com.udacity.hotel.models.*;

public class PretendDataBaseHotelRepo implements HotelRepository
{
	Map<String, Customer> customers;
	
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
}
