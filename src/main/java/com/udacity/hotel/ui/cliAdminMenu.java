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
		
		public boolean isAuthentic()
		{
			String pw; // admin password
			
			//CLI = new Scanner(System.in);
			
			int pwHash = -2038048907; // masteryoda, nbtufszpeb, adoyretsam, bepzsfutbn
			
			System.out.print("Please enter the Admin password: ");
			
			pw = cliMainMenu.CLI.nextLine();
			
			if( pwHash == pw.hashCode() )
				{ System.out.println(); return true; }
			else
				{ System.out.println("\nIncorrect Password!!\n"); return false; }
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
						//AR.displayAllReservations();
						//System.out.println("\n");
					break;
				
					case 4:
						addRooms();
					break;
				
					case 5:
						//CLI.close();
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
			Collection<IRoom> rooms = AR.getAllRooms();
			
			if (rooms.size() == 0)
				System.out.println("\nThere Are No Rooms To Display\n");
			else
			{
				//System.out.println("\nHotel Rooms Listing");

				for(IRoom R: rooms)
					System.out.println("\n" + R + "\n");
			
				System.out.println();
			}
		}

		@Override
		public void addRooms()
		{
			HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
			String ans, roomNumber, type;
			Double price;

			RoomType RT;

			System.out.println();

			do {

				while(true)
				{
					System.out.print("Enter room number: ");
					roomNumber = cliMainMenu.CLI.nextLine();
					
					if( roomNumber.length() > 0 )
					{
						if( isRoomNumValid(roomNumber) )
						{
							roomNumber = roomNumber.toUpperCase();
							
							if( AR.roomExist(roomNumber) || rooms.containsKey(roomNumber) )
								System.out.println("\nThere is already a room: [" + roomNumber + "]\n");
							else
								break;
						}
						else
							System.out.println("\n[" + roomNumber + "] Is Not A Valid Room Number\n");
					}
				}

				while(true)
				{
					try
					{	
						System.out.print("Enter price per night: ");
						price = Double.valueOf( cliMainMenu.CLI.nextLine() );
						break;
					}

					catch(Exception ex)
					{
						System.out.println("\nInvalid Input!!\n");
						continue;
					}
				}

				while(true)
				{
					System.out.print("Enter room type: 1 for single bed, 2 for double bed: ");
					type = cliMainMenu.CLI.nextLine();

					if( type.length() == 0 )
						continue;

					if( "1 2".contains(type) )
						break;
				}

				if( type.compareTo("1") == 0 )
					RT = RoomType.SINGLE;
				else
					RT = RoomType.DOUBLE;

				if (price == 0.0)
					rooms.put( roomNumber, new FreeRoom(roomNumber, RT) );
				else
					rooms.put( roomNumber, new Room(roomNumber, price, RT) );
				
				System.out.println( "\n" + rooms.get(roomNumber) );

				do {
				
					System.out.print("\nWould you like to add another room (Y/N)? ");
					ans = cliMainMenu.CLI.nextLine();

				} while( (ans.toLowerCase().compareTo("yes") != 0) && (ans.toLowerCase().compareTo("y") != 0) && (ans.toLowerCase().compareTo("no") != 0) && (ans.toLowerCase().compareTo("n") != 0) );
				
				System.out.println();

			} while( (ans.toLowerCase().compareTo("yes") == 0) || (ans.toLowerCase().compareTo("y") == 0) );

			AR.addRooms( new ArrayList<IRoom>( rooms.values() ) );
		}
		// end addRooms()
		
		
		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			 for(int i = 0; i < 35; i++)
		 		line += "*";

			items = "\n1.\tSee all Customers\n2.\tSee all Rooms\n3.\tSee all Reservations\n4.\tAdd a Room\n5.\tBack to Main Menu\n";

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

