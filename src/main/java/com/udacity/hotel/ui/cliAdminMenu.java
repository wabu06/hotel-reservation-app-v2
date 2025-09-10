package com.udacity.hotel.ui;


import java.util.*;
//import java.util.Scanner;
//import java.util.regex.*;

//import com.google.gson.Gson;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;


public class cliAdminMenu
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
						showRoomRates();
					break;
					
					case 7:
						displayRoomReservations();
					break;
				
					case 8:
						getVacantInTimeframe();
					break;
				
					case 9:
						changeRoomOfReservation();
					break;
				
					case 10:
						showCustomerReservations();
					break;
					
					case 11:
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
		

		void getVacantInTimeframe()
		{
			String cidEntry, codEntry; // check IN/OUT dates, as entered by user
			
			Date cid, cod;
			
			while(true)
			{
				try
				{
					System.out.print("\nPlease enter a check in date as (mm/dd/yyy): ");
					cidEntry = cliMainMenu.CLI.nextLine();
					cid = getDateInstance(cidEntry, 11);
					break;
				}
				catch(Exception ex) {
					System.out.println("\n" + ex.getMessage() );
				}
			}
			
			while(true)
			{
				try
				{
					System.out.print("\nPlease enter a check out date as (mm/dd/yyy): ");
					codEntry = cliMainMenu.CLI.nextLine();
					cod = getDateInstance(codEntry, 15);
					break;
				}
				catch(Exception ex) {
					System.out.println("\n" + ex.getMessage() );
				}
			}
			
			for(Room rm: AR.findVacantInTime(cid, cod))
				System.out.println("\n" + rm);
			
			System.out.println();
		}

		void displayAllReservations()
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
		
		void displayAllRooms()
		{
			Collection<Room> rooms = AR.getAllRooms();
			
			//Gson gson = new Gson();

			for(Room R: rooms)
				//System.out.println("\n" + gson.toJson(R) + "\n");
				System.out.println("\n" + R + "\n");
			
			System.out.println();
		}
		
		void showRoomRates()	{
			System.out.println("\nSingle Rooms Current Price Per Night: $" + AR.getSinglesPrice() + "\nDouble Rooms Current Price Per Night: $" + AR.getDoublesPrice() + "\n");
		}
		
		private Double getPrice()
		{
			Double price;
			
			while(true)
			{
				try
				{	
					System.out.print("\nEnter new price per night: ");
					price = Double.parseDouble( cliMainMenu.CLI.nextLine() );
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
		
		void changePriceForSingles()
		{
			AR.changePriceForRooms(getPrice(), RoomType.SINGLE);
		} // end price change

		void changePriceForDoubles()
		{
			AR.changePriceForRooms(getPrice(), RoomType.DOUBLE);
		} // end price change
		
		private String getRoomNumber(String prompt)
		{
			String roomNumber;
			
			while(true)
			{
				System.out.print(prompt);
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
			
			return roomNumber;
		}
		
		void displayRoomReservations()
		{
			String roomNumber = getRoomNumber("\nPlease Enter The Room Number: ");
			
			Collection<Reservation> reserves = AR.getRoomReservations(roomNumber);
			
			if(reserves.size() == 0)
			{
				System.out.println("\nThere Are No Reservations For Room " + roomNumber + "\n");
				return;
			}
			
			//System.out.println("\nThese Are The Reservations For Room: " + roomNumber + "\n");
			
			for(Reservation R: reserves)
				System.out.println("\n" + R + "\n");
		}
		
		void changeRoomOfReservation()
		{
			String roomNumber = getRoomNumber("\nPlease Enter Old Room Number: ");
			
			Collection<Reservation> reserves = AR.getRoomReservations(roomNumber);
			
			if(reserves.size() == 0)
			{
				System.out.println("\nThere Are No Reservations For Room " + roomNumber + "\n");
				return;
			}
			
			for(Reservation R: reserves)
				System.out.println("\n" + R + "\n");
			
			Optional<Reservation> RO;
			int ID; // room number/ID
			
			while(true)
			{
				try {
					System.out.print("\nPlease Enter Reservation ID: ");
					ID = Integer.parseInt( cliMainMenu.CLI.nextLine() );
				}
				catch(Exception exp) {
					System.out.println("\nInvalid Entry!!\n");
					continue;
				}
				
				RO = AR.getReservationByID(reserves, ID);
				
				if(RO.isEmpty())
					System.out.println("\nInvalid ID!!\n");
				else
					break;
			}
			
			roomNumber = getRoomNumber("\nPlease Enter New Room Number: ");
			
			if( AR.changeRoom(roomNumber, RO.get()) )
				System.out.println("\n" + RO.get() + "\n");
			else
				System.out.println("\nUnable To Change Rooms!!\n");
		}

		private String getEmail()
		{
			String email;
			boolean valid;
			
			do
			{
				System.out.print("\nPlease enter customer's email address, as name@domain.com: ");
				email = cliMainMenu.CLI.nextLine();

				valid = isEmailValid(email);

			} while(!valid);
			
			return email;
		}
		
		void showCustomerReservations()
		{
			String email = getEmail();
			
			Collection<Reservation> reserves = AR.getCustomerReservations(email);
			
			if(reserves.isEmpty())
			{
				System.out.println("\nCustomer Has No Reservations!!\n");
				return;
			}
			
			for(Reservation R: reserves)
				System.out.println("\n" + R + "\n");
		}
				
		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			 for(int i = 0; i < 45; i++)
		 		line += "*";

			items = "\n1.\tSee all Customers\n2.\tSee all Rooms\n3.\tSee all Reservations\n4.\tChange Price For Single Rooms\n5.\tChange Price For Double Rooms\n6.\tSee Current Room Rates\n7.\tSee All Reservations For A Room\n8.\tShow Rooms Vacant For A Particular Check-in & Check-out Date\n9.\tChange The Room Of A Reservation\n10.\tShow All Reservations For A Customer\n11.\tBack to Main Menu\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Admin Menu\n" + line + items + line + prompt;
		}

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

