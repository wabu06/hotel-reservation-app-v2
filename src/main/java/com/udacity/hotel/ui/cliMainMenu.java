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
						displayCustomerReservations();
					break;
				
					case 3:
						createAccount( getEmail() );
					break;
				
					case 4:
						System.out.println();
						AM.adminMenuManager();
					break;
				
					case 5:
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

		@Override
		public void displayCustomerReservations()
		{
			String email = getEmail();
			String pin = getPin();
			
			if( !HR.isAuthentic(email, pin) )
			{
				System.out.println("\nInvalid Credentials!!\n");
				return;
			}
			
			Collection<Reservation> reserves = HR.getCustomerReservations(email);
			
			if (reserves == null)
				System.out.println("\nYou Have No Reservations\n");
			else
			{ 
				//System.out.println("\nYour Reservations Are:\n");
			
				for(Reservation R:  reserves)
					System.out.println("\n" + R + "\n");
			}
		}

		@Override
		public String getEmail()
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

			String ans;
			
			String cidStr, codStr; // check IN/OUT dates, as entered by user
			
			Date cid, cod; // check IN/OUT dates
			
			Calendar cal;
			
			HashMap<String, IRoom> rooms;
			String rm;
			
			while(true)
			{
				while(true)
				{
					try
					{
						System.out.print("\nPlease enter your check in date as (mm/dd/yyy): ");
						cidStr = CLI.nextLine();
						cid = getDateInstance(cidStr, 11);
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
						codStr = CLI.nextLine();
						cod = getDateInstance(codStr, 15);
						break;
					}
					catch(Exception ex)
						{ System.out.println("\n" + ex.getMessage() ); }
				}
			
				if( !cid.before(cod) )
					System.out.println("\nThe check in date must precede the check out date!!");
				else
				{
					rooms = HR.findARoom(cid, cod);
					
					if (rooms.size() == 0)
					{
						rooms = HR.recommendedRooms(cid, cod);
						
						if( rooms.size() > 0 )
						{
							System.out.println("\nThere are no rooms available for the check in/out dates entered, so the following is being recommended:\n");
							
							for(IRoom R: rooms.values() )
								System.out.println(R + "\n");
							
							System.out.print("\nWould you like to select from the recommendations?(Y/n) ");
							ans = CLI.nextLine();
							
							if( ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no") )
							{
								System.out.print("Would you like try again with different check in/out dates?(Y/n) ");
								ans = CLI.nextLine();
								
								if( ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no") )
									{ System.out.println("\n"); break; }
								else
									continue;
							}
							
							System.out.print("\nPlease select by entering a room number from above: ");
							rm = CLI.nextLine(); rm = rm.toUpperCase();
							
							while( !rooms.containsKey(rm) )
							{
								System.out.println("\n[" + rm + "] is not a valid choice, the rooms available are as follows:\n");

								for(IRoom R: rooms.values() )
									System.out.println(R + "\n");

								System.out.print("\nPlease select by entering a room number from above: ");
								rm = CLI.nextLine(); rm = rm.toUpperCase();
							} 
							
							System.out.println( "\n" + HR.bookARoom(email, HR.getRoom(rm), cid, cod) + "\n");
							break;
						}
						else
						{
							System.out.print("\nSorry there are no rooms available, Would you like try again with different check in/out dates?(Y/n) ");
							ans = CLI.nextLine();
						
							if( (ans.toLowerCase().compareTo("n") == 0) || (ans.toLowerCase().compareTo("no") == 0) )
								{ System.out.println("\n"); break; }
						}
					}
					// if room size = 0					
					else
					{
						do {
				
							System.out.println("\nRooms available are:\n");
				
							for(IRoom R: rooms.values() )
								System.out.println(R + "\n");

							System.out.print("\nPlease select by entering a room number from above: ");
							rm = CLI.nextLine(); rm = rm.toUpperCase();
				
						} while( !rooms.containsKey(rm) );
				
						System.out.println( "\n" + HR.bookARoom(email, HR.getRoom(rm), cid, cod) + "\n");
						break; 
					}
				}
				// end outer else
			}
			// end of outermost while
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
			
			Customer C = HR.createACustomer(email, firstName, lastName, pinHash);
				
			System.out.println("\n" +  C + "\n");
			
			return C;
		}

		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			for(int i = 0; i < 35; i++)
		 		line += "#";

			items = "\n1.\tReserve a room\n2.\tSee my reservations\n3.\tCreate an account\n4.\tAdmin\n5.\tExit\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Main Menu\n" + line + items + line + prompt;
		}
}
