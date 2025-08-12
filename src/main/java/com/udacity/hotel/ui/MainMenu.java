package com.udacity.hotel.ui;


import com.udacity.hotel.models.*;


public interface MainMenu
{
	public MainMenu launch();
	public void mainMenuManager();
	void reserveRoom();
	void displayCustomerReservations();
	Customer createAccount(String email);
}

