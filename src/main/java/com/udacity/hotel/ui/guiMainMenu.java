package com.udacity.hotel.ui;


//import java.util.*;
//import java.util.regex.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;

public class guiMainMenu implements MainMenu
{
	private guiMainMenu() {}
	
	final static guiMainMenu mmInstance = new guiMainMenu();
	
	public static guiMainMenu getInstance() { return mmInstance; }
	
	@Override
	public MainMenu launch() { return mmInstance; }
	
	@Override
	public void mainMenuManager() {}
	
	@Override
	public String getEmail() { return "jdoe@john.com"; }
	
	@Override
	public void reserveRoom() {}
	
	@Override
	public void displayCustomerReservations() {}
	
	@Override
	public Customer createAccount(String email) { return new Customer("jane", "doe", "jdoe@john.com"); }
}
