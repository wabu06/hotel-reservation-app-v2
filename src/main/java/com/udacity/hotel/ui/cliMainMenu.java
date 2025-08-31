package com.udacity.hotel.ui;


import java.util.*;
//import java.util.Scanner;
import java.util.regex.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;


public class cliMainMenu implements MainMenu
{
		static Scanner CLI = new Scanner(System.in);
		
		HotelResource HR;
		AdminResource AR;

		final static cliMainMenu mmInstance = new cliMainMenu();
		public static cliMainMenu getInstance() { return mmInstance; }
		
		@Override
		public MainMenu launch()
		{
			System.out.println("WABU's Hotel Reservation Application v1.0.0\n");
			return mmInstance;
		}

		private cliMainMenu()
		{
			HR = HotelResource.getInstance();
			AR = AdminResource.getInstance();
		}
		
		@Override
		public void mainMenuManager()
		{
			cliAdminMenu AM = cliAdminMenu.getInstance();
			
			int choice;
		
			while(true)
			{
				System.out.print(this); // print main menu

					//get menu selection from user
				try 
					{ choice = Integer.parseInt( CLI.nextLine() ); }
			
				catch(Exception ex)
					{ System.out.println("\nInvalid Input!!\n"); continue; }
					
				switch(choice)
				{
					case 1:
						reserveRoom();
					break;
					
					case 2:
						changeReservation();
					break;
					
					case 3:
						cancelAReservation();
					break;
				
					case 4:
						displayCustomerReservations();
					break;
				
					case 5:
						createAccount( getEmail() );
					break;
				
					case 6:
						System.out.println();
						AM.adminMenuManager();
					break;
				
					case 7:
						CLI.close();
					return;

					default:
						System.out.println("\nInvalid Input!!\n");
				}
				// end of switch
			}
			// end of while
		}
		// end mainMenuManager
		
		String getPin()
		{
			String pin;
			Optional<String> emsg;
			
			do
			{
				System.out.print("\nPlease enter your six digit pin: ");
				pin = CLI.nextLine();

				emsg = validatePin(pin);
				
				if(emsg.isPresent())
					System.out.println('\n' + emsg.get());

			} while(emsg.isPresent());
			
			return pin;
		}
		
		String validateCredentials()
		{
			String email = getEmail();
			String pin = getPin();
			
			if( !HR.isAuthentic(email, pin) )
			{
				System.out.println("\nInvalid Credentials!!\n");
				return null;
			}
			else
				return email;
		}

		@Override
		public void displayCustomerReservations()
		{
			String email = validateCredentials();
			
			if(email == null)
				return;
			
			Collection<Reservation> reserves = HR.getAllCustomerReservations(email);
			
			if (reserves == null)
				System.out.println("\nYou Have No Reservations\n");
			else
			{ 
				//System.out.println("\nYour Reservations Are:\n");
			
				for(Reservation R:  reserves)
					System.out.println("\n" + R + "\n");
			}
		}
		
		String getPhoneNumber()
		{
			String number;
			
			do
			{
				System.out.print("\nPlease enter your phone number, as (###-###-####): ");
				number = CLI.nextLine();
			}
			while( !isPhoneNumberValid(number) );
			
			return number;
		}

		String getEmail()
		{
			String email;
			boolean valid;
			
			do
			{
				System.out.print("\nPlease enter your email address, as name@domain.com: ");
				email = CLI.nextLine();

				valid = isEmailValid(email);

			} while(!valid);
			
			return email;
		}
		
		public void cancelAReservation()
		{
			String email = validateCredentials();
			
			if(email == null)
				return;
			
			Collection<Reservation> reserves = HR.getNonCanceledReservations(email);
			
			if(reserves.isEmpty()) {
				System.out.println("\nThere are no Reservations to Cancel!!\n");
				return;
			}
			
			int select;
			
			Optional<Reservation> RO = Optional.empty();
			
			do
			{
				for(Reservation R: reserves)
					System.out.println(R + "\n");
			
				System.out.print("\nSelect Reservation to cancel, by entering the corresponding Reservation ID: ");
			
				try {
					select = Integer.parseInt( CLI.nextLine() );
					System.out.println();
				}
				catch(Exception ex) {
					System.out.println("\nInvalid Input!!\n");
					continue;
				}
				
				RO = HR.cancelReservation(reserves, select);
			}
			while(RO.isEmpty());
			
			System.out.println("Selected Reservation Has Been Canceled:\n");
			
			System.out.println(RO.get() + "\n");
			
		}
		
		ResInfo searchForRooms()
		{
			String cidEntry, codEntry; // check IN/OUT dates, as entered by user
			
			Date cid = null;
			Date cod = null; // check IN/OUT dates
			
			ResInfo RI = null;
			
			while(true)
			{
				while(true)
				{
					try
					{
						System.out.print("\nPlease enter your check in date as (mm/dd/yyy): ");
						cidEntry = CLI.nextLine();
						cid = getDateInstance(cidEntry, 11);
						break;
					}
					catch(Exception ex)
						{ System.out.println("\n" + ex.getMessage() ); }
				}
			
				while(true)
				{
					try
					{
						System.out.print("\nPlease enter your check out date as (mm/dd/yyy): ");
						codEntry = CLI.nextLine();
						cod = getDateInstance(codEntry, 15);
						break;
					}
					catch(Exception ex)
						{ System.out.println("\n" + ex.getMessage() ); }
				}

				Room room;
				
				String typeEntry;
				RoomType type;
							
				if( !cid.before(cod) )
					System.out.println("\nThe check in date must precede the check out date!!");
				else
				{
					do
					{
						System.out.print("Select Room Type as, D for Double, S for Single: ");
						typeEntry = CLI.nextLine();
					}
					while( (typeEntry.toUpperCase().compareTo("S") != 0) && (typeEntry.toUpperCase().compareTo("D") != 0) );
					
					if(typeEntry.toUpperCase().compareTo("S") == 0)
						type = RoomType.SINGLE;
					else
						type = RoomType.DOUBLE;
					
					room = HR.findRoom(cid, cod, type); 
					
					if(room == null)
					{
						String ans;
						
						System.out.println("\nThere are no rooms available for the check in/out dates entered, would you like to try again?(Y/n) ");
						ans = CLI.nextLine();
						
						if( ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no") )
							break;
					}
					else
					{
						RI = new ResInfo(room.getRoomNumber(), cid, cod);
						break;
					}
				}
					
			return RI; //new ResInfo(rm, cid, cod);
		}
		
		void changeReservation()
		{
			String email = validateCredentials();
			
			if(email == null)
				return;
			
			Collection<Reservation> reserves = HR.getNonCanceledReservations(email);
			
			if(reserves == null) {
				System.out.println("\nYou Have No Reservations\n");
				return;
			}
			
			System.out.println("\nYou Have The Following Reservations:\n");
			
			for(Reservation r: reserves)
				System.out.println( "\n" + r + "\n");
				
			int ID;
			
			Optional<Reservation> RO;
			
			do
			{
				System.out.print("\nSelect Reservation You Would Like To Change, Using Reservation ID: ");
				
				try {
					ID = Integer.parseInt( CLI.nextLine() );
				}
				catch(Exception ex) {
					System.out.println("\nInvalid Input!!\n");
					continue;
				}
				
				RO = HR.getReservationByID(reserves, ID);
				
				if(RO.isEmpty()) {
					System.out.println("\nThere's No Reservation With ID Entered!!\n");
					continue;
				}
			
				break;
			}
			while(true);
			
			Reservation reservation = RO.get();
			
			HR.removeReservation(reservation);
			
			ResInfo RI = searchForRooms();
			
			if(RI == null) { // need to add back removed reservation to room
				HR.restoreReservation(reservation);
				return;
			}
			
			String rm = RI.getRoom(); // room number
			
			Date cid = RI.getCheckInDate();
			Date cod = RI.getCheckOutDate();
			
			System.out.println( "\n" + HR.changeReservation(reservation, rm, cid, cod) + "\n");
		}
		
		@Override
		public void reserveRoom()
		{
			String email = getEmail();
			
			Customer C = HR.getCustomer(email);

			if( C == null )
				C = createAccount(email);
			else
			{
				String pin = getPin();
			
				if( !HR.isAuthentic(email, pin) )
				{
					System.out.println("\nInvalid Credentials!!\n");
					return;
				}
			}
			
			ResInfo RI = searchForRooms();
			
			String rm = RI.getRoom();
			
			Date cid = RI.getCheckInDate();
			Date cod = RI.getCheckOutDate();
			
			System.out.println( "\n" + HR.bookRoom(email, HR.getRoom(rm), cid, cod) + "\n");
		}
		// end of reserveRoom()

		@Override 
		public Customer createAccount(String email)
		{
			String lastName, firstName;
			
			while( HR.getCustomer(email) != null )
			{
				System.out.println("\nThere Is An Existing Account With The Email: <" + email + ">, Please enter another email address!!");
				email = getEmail();
			}
			
			long pinHash = getPin().hashCode();

			System.out.print("\nPlease enter your last name: ");
			lastName = CLI.nextLine();

			System.out.print("Please enter your first name: ");
			firstName = CLI.nextLine();
			
			String number = getPhoneNumber();
			
			Customer C = HR.createACustomer(number, email, firstName, lastName, pinHash);
				
			System.out.println("\n" +  C + "\n");
			
			return C;
		}

		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			for(int i = 0; i < 35; i++)
		 		line += "#";

			items = "\n1.\tReserve A Room\n2.\tChange A Reservation\n3.\tCancel A Reservation\n4.\tSee My Reservations\n5.\tCreate An Account\n6.\tAdmin\n7.\tExit\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Main Menu\n" + line + items + line + prompt;
		}
		
		public class ResInfo
		{
			private String room;
			
			private Date checkInDate; 
			private Date checkOutDate;
			
			public ResInfo(String rm, Date cid, Date cod)
			{
				room = rm;
				checkInDate = cid;
				checkOutDate = cod;
				
			}
			
			String getRoom() {
				return room;
			}	
			
			Date getCheckInDate() {
				return checkInDate;
			}
			
			Date getCheckOutDate() {
				return checkOutDate;
			}
		}
}
