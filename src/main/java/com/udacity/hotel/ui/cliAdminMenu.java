package com.udacity.hotel.ui;


import java.util.*;
//import java.util.Scanner;
//import java.util.regex.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;


public class cliAdminMenu implements AdminMenu
{
		//Scanner CLI;
		
		HotelResource HR;
		AdminResource AR;

		final static cliAdminMenu amInstance = new cliAdminMenu();
		public static cliAdminMenu getInstance() { return amInstance; }

		private cliAdminMenu()
		{
			HR = HotelResource.getInstance();
			AR = AdminResource.getInstance();
		}
		
		boolean isAuthentic()
		{
			int pwHash = AR.getPasswordHash();
			
			String pw; // admin password
			
			if(pwHash == 0)
			{
				System.out.print("Please Select An Admin password: ");
				pw = cliMainMenu.CLI.nextLine();
				AR.savePasswordHash(pw);
				System.out.println();
				return true;
			}
			
			System.out.print("Please enter the Admin password: ");
			pw = cliMainMenu.CLI.nextLine();
			
			if( pwHash == pw.hashCode() )
			{
				System.out.println();
				return true;
			}
			else
			{
				System.out.println("\nIncorrect Password!!\n");
				return false;
			}
		}
		
		@Override
		public void adminMenuManager()
		{
			if( !isAuthentic() )
				return;
			
			int choice;

			while(true)
			{
				System.out.print(this); // print admin menu
			
					//get menu selection from user
				try 
					{ choice = Integer.parseInt( cliMainMenu.CLI.nextLine() ); }
			
				catch(Exception ex)
					{ System.out.println("\nInvalid Input!!\n"); continue; }

				switch(choice)
				{
					case 1:
						displayAllCustomers();
					break;
				
					case 2:
						displayAllRooms();
					break;
				
					case 3:
						displayAllReservations();
					break;
				
					case 4:
						changePriceForSingles();
					break;
				
					case 5:
						changePriceForDoubles();
					break;
					
					case 6:
						displayRoomReservations();
					break;
				
					case 7:
						System.out.println(); // return to main menu
						return;
				
					default:
						System.out.println("\nInvalid Input!!\n");
				}
				// end switch
			}
			// end of while
		}
		// end of adminMenuManager()
		
		@Override
		public void displayAllReservations()
		{
			Collection<Reservation> reserves = AR.getAllReservations();
		
			if(reserves.size()  == 0)
				System.out.println("\nThere Are No Reservations To Display\n");
			else
			{
				for(Reservation R: reserves)
					System.out.println("\n" + R + "\n");
			}
		}
		
		@Override
		public void displayAllRooms()
		{
			Collection<Room> rooms = AR.getAllRooms();

			for(IRoom R: rooms)
				System.out.println("\n" + R + "\n");
			
			System.out.println();
		}
		
		Double getPrice()
		{
			Double price;
			
			while(true)
			{
				try
				{	
					System.out.print("Enter new price per night: ");
					price = Double.valueOf( cliMainMenu.CLI.nextLine() );
					break;
				}
				catch(Exception ex)
				{
					System.out.println("\nInvalid Input!!\n");
					continue;
				}
			}
			
			return price;
		}
		
		@Override
		public void changePriceForSingles()
		{
			AR.changePriceForRooms(getPrice(), RoomType.SINGLE);
		} // end price change
		
		@Override
		public void changePriceForDoubles()
		{
			AR.changePriceForRooms(getPrice(), RoomType.DOUBLE);
		} // end price change
		
		public void displayRoomReservations()
		{
			String roomNumber;
			
			while(true)
			{
				System.out.print("Please Enter The Room Number: ");
				roomNumber = cliMainMenu.CLI.nextLine();
					
				if( roomNumber.length() > 0 )
				{
					if( isRoomNumValid(roomNumber) )
					{
						roomNumber = roomNumber.toUpperCase();
							
						if( !AR.roomExist(roomNumber) )
							System.out.println("\nThere is no room with the number: [" + roomNumber + "]\n");
						else
							break;
					}
					else
						System.out.println("\n[" + roomNumber + "] Is Not A Valid Room Number\n");
				}
			}
			
			Collection<Reservation> reserves = AR.getRoomReservations(roomNumber);
			
			if(reserves.size() == 0)
			{
				System.out.println("\nThere Are No Reservations For Room " + roomNumber + "\n");
				return;
			}
			
			System.out.println("\nThese Are The Reservations For Room: " + roomNumber + "\n");
			
			for(Reservation R: reserves)
				System.out.println("\n" + R + "\n");
		}
				
		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			 for(int i = 0; i < 45; i++)
		 		line += "*";

			items = "\n1.\tSee all Customers\n2.\tSee all Rooms\n3.\tSee all Reservations\n4.\tChange Price For Single Rooms\n5.\tChange Price For Double Rooms\n6.\tSee All Reservations For A Room\n7.\tBack to Main Menu\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Admin Menu\n" + line + items + line + prompt;
		}

		@Override
		public void displayAllCustomers()
		{
			Collection<Customer> customers = AR.getAllCustomers();

			if (customers.size() == 0)
				System.out.println("\nThere Are No Customers To Display\n");
			else
			{
				for(Customer C: customers)
					System.out.println("\n" + C + "\n");
			}
		}
}

