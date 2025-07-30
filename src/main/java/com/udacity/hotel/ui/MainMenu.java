package com.udacity.hotel.ui;


import com.udacity.hotel.models.*;


public interface MainMenu
{
	public MainMenu launch();
	public void mainMenuManager();
	String getEmail();
	void reserveRoom();
	void displayCustomerReservations();
	Customer createAccount(String email);
}

