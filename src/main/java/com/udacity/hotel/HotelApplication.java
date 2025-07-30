// HotelApplication.java

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


public class HotelApplication
{
	public static void main(String[] args)
	{
		//MainMenu mm;
		
		if(args.length == 1)
		{
			if( args[0].toLowerCase().equals("gui") )
				//guiMainMenu.getInstance();
				Application.launch(HotelMenuGui.class, args);
			else
			cliMainMenu.getInstance().launch().mainMenuManager();
		}
		else
			cliMainMenu.getInstance().launch().mainMenuManager();
		
		//mm.launch().mainMenuManager();
		
		//MainMenu.launch().mainMenuManager();
	}
}

