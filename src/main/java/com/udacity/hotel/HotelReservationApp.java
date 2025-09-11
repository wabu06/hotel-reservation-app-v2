/**
 * @title Hotel Reservation Application
 *
 *
 * @author W. K.  Burke
 *
 */

package com.udacity.hotel;


import javafx.application.Application;

import com.udacity.hotel.ui.*;


public class HotelReservationApp
{
	public static void main(String[] args)
	{
		if(args.length == 1)
		{
			if( args[0].toLowerCase().equals("gui") )
				Application.launch(MenuWindow.class, args);
			else
				cliMainMenu.getInstance().launch().mainMenuManager();
		}
		else
			cliMainMenu.getInstance().launch().mainMenuManager();
	}
}

