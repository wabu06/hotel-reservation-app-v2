package com.udacity.hotel.data;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.*;
import java.util.prefs.Preferences;

import java.io.InputStream;

//import java.util.stream.Collectors;

//import java.util.function.Function;

import com.udacity.hotel.models.*;


public class PretendDataBaseHotelRepo implements HotelRepository
{
	Random rng;

	Map<String, Customer> customers;
	
	Map<String, Room> rooms;
	Map< String, ArrayList<Reservation> > singles;
	Map< String, ArrayList<Reservation> > doubles;
	
	final static PretendDataBaseHotelRepo instance = new PretendDataBaseHotelRepo();
	
	public static PretendDataBaseHotelRepo getInstance() { return instance; }
	
		//preference keys
  private static final String CUSTOMERS = "CUSTOMERS";
  private static final String SINGLES = "SINGLES";
  private static final String DOUBLES = "DOUBLES";

  //private static final Preferences repo_prefs = Preferences.userNodeForPackage(PretendDataBaseHotelRepo.class);
  private Preferences repo_prefs;
  private Gson gson; //used to serialize objects into JSON
  
  private PretendDataBaseHotelRepo()
  {
  	rng = new Random();
  	
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
  	
//    if(singlesString == null)
//    	singles = new HashMap< String, ArrayList<Reservation> >();
//    else
//    {
//    	Type type = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
//      singles = gson.fromJson(singlesString, type);
//    }
    
    String doublesString = repo_prefs.get(DOUBLES, null);
    
//    if(doublesString == null)
//    	doubles = new HashMap< String, ArrayList<Reservation> >();
//    else
//    {
//    	Type type = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
//      doubles = gson.fromJson(doublesString, type);
//    }
    
    String roomString = repo_prefs.get("ROOMS", null);
    
    if( (roomString == null) || (singlesString == null) || (doublesString == null) )
    	createRooms();
    else
    {
    	Type roomType = new TypeToken< Map<String, Room> >() {}.getType();
      rooms = gson.fromJson(roomString, roomType);
      
      Type singleType = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
      singles = gson.fromJson(singlesString, singleType);
      
      Type doubleType = new TypeToken< Map< String, ArrayList<Reservation> > >() {}.getType();
      doubles = gson.fromJson(doublesString, doubleType);
    }
  }
  
  private RoomType getRoomType()
	{
		if(rng.nextBoolean())
			return RoomType.DOUBLE;
		else
			return RoomType.SINGLE;
	}
		
	private void createRooms()
	{
		singles = new HashMap< String, ArrayList<Reservation> >();
		doubles = new HashMap< String, ArrayList<Reservation> >();
		
		Properties props = new Properties();
		
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("rooms.properties")) {
     	props.load(is);
    }
    	//catch (IOException ioe ) {
    catch (Exception exp ) {
       	//price.properties not found
     	System.exit(1);
    }
    	
    Integer rpf = Integer.parseInt( props.getProperty("rooms.per.floor") );
    Integer floors = Integer.parseInt( props.getProperty("floors") );
    	
    rooms = new HashMap<String, Room>();
    	
    for(int f = 2; f <= floors; f++)
    {
    	for(int r = 1; r <= rpf; r++)
    	{
    		String roomID = String.valueOf(100*f + r);
    			
    		RoomType type = getRoomType();
    			
    		rooms.put(roomID, new Room(roomID, type));
    			
    		if(type == RoomType.SINGLE)
    			singles.put(roomID, new ArrayList<Reservation>());
    		else
    			doubles.put(roomID, new ArrayList<Reservation>());
    	}
    }
    repo_prefs.put("ROOMS", gson.toJson(rooms));
    repo_prefs.put(SINGLES, gson.toJson(singles));
    repo_prefs.put(DOUBLES, gson.toJson(doubles)); 
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
  public Map<String, Room> getRooms() {
  	return rooms;
  }
  
  @Override
  public void updateRooms(Map<String, Room> new_rooms) {
  	repo_prefs.put("ROOMS", gson.toJson(new_rooms));
  }
  
  @Override
  public Map< String, ArrayList<Reservation> > getSingles() {
  	return singles;
  }
  
  @Override
  public Map< String, ArrayList<Reservation> > getDoubles() {
  	return doubles;
  }
  
  @Override
  public void updateSingles(Map< String, ArrayList<Reservation> > new_singles) {
  	repo_prefs.put(SINGLES, gson.toJson(new_singles));
  }
  
  @Override
  public void updateDoubles(Map< String, ArrayList<Reservation> > new_doubles) {
		repo_prefs.put(DOUBLES, gson.toJson(new_doubles));  
  }
}

