package com.udacity.hotel.services;


import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

//import java.io.IOException;
//import java.io.InputStream;

//import java.util.Properties;

import com.udacity.hotel.models.*;
import com.udacity.hotel.data.*;


public class ReservationService
{ 	
	 	Map<String, Room> rooms;
		
		//Map< String, ArrayList<Reservation> > singles;
		//Map< String, ArrayList<Reservation> > doubles;
		
		PretendDataBaseHotelRepo hotelRepo;
		
		final static ReservationService instance = new ReservationService();
		
		public static ReservationService getInstance() { return instance; }
		
		private ReservationService()
		{ 
			hotelRepo = PretendDataBaseHotelRepo.getInstance();
			
			rooms = hotelRepo.getRooms();
			//singles = hotelRepo.getSingles();
			//doubles = hotelRepo.getDoubles();
			
			//addRooms();
		}
    
    public void changePriceForRooms(Double price, RoomType type)
    {
    	if(type == RoomType.SINGLE)
    	{
    		Room.setSinglePrice(price);
    		rooms.values().stream().filter(rm -> rm.getRoomType() == RoomType.SINGLE).forEach(rm -> rm.setRoomPrice());
    		hotelRepo.updateRooms(rooms);
    	}
    	else
    	{
    		Room.setDoublePrice(price);
    		rooms.values().stream().filter(rm -> rm.getRoomType() == RoomType.DOUBLE).forEach(rm -> rm.setRoomPrice());
    		hotelRepo.updateRooms(rooms);
    	}
    }
		
//		public void addRoom(Room room)
//		{
//			rooms.put(room.getRoomNumber(), room);
//			
//			if(room.getRoomType() == RoomType.SINGLE) {
//				singles.put(room.getRoomNumber(), new ArrayList<Reservation>());
//				hotelRepo.updateSingles(singles);
//			}
//			else {
//				doubles.put(room.getRoomNumber(), new ArrayList<Reservation>());
//				hotelRepo.updateDoubles(doubles);
//			}
//		}
		
		public Room getARoom(String roomId) {
			return rooms.get(roomId);
		}
		
		public boolean roomExist(String roomId) {
			return rooms.containsKey(roomId);
		}
		
		public Collection<Room> getRooms() {
			return rooms.values();
		}
		
		public int getRoomCount() {
			return rooms.size();
		}
		
		public Reservation removeReservation(Reservation R)
		{
			String roomID = R.getRoom().getRoomNumber();
			
			rooms.get(roomID).getReservations().remove(R);
			
			return R;
		}
		
		public Reservation restoreReservation(Reservation R)
		{
			String roomID = R.getRoom().getRoomNumber();
			
			rooms.get(roomID).getReservations().add(R);
			
			return R;
		}
		
		public Reservation changeReservation(Reservation R, String roomID, Date cid, Date cod)
		{
			Room room = rooms.get(roomID);
			
			R.setRoom(new Room(room));
			
			long diff = cod.getTime() - cid.getTime();
			
			long staylength = TimeUnit.MILLISECONDS.toDays(diff) + 1;
			
			Double totalCost = staylength * room.getRoomPrice();
			
			R.setStayLength(staylength);
			R.setTotal(totalCost);
			
			R.setCheckInDate(cid);
			R.setCheckOutDate(cod);
			
			room.getReservations().add(R);
			
			hotelRepo.updateReservations(room);

			return R;
		}
		
		public Reservation reserveARoom(Customer customer, Room room, Date checkInDate, Date checkOutDate)
		{
			// first check to see if room is available for the given dates
			
			long diff = checkOutDate.getTime() - checkInDate.getTime();
			
			long staylength = TimeUnit.MILLISECONDS.toDays(diff) + 1;
			
			Double totalCost = staylength * room.getRoomPrice();
			
			Reservation R;
			
			R = new Reservation(customer, new Room( room ), staylength, totalCost, checkInDate, checkOutDate);
			
			room.getReservations().add(R);
			
			hotelRepo.updateReservations(room);
			
			return R;
		}
		
		public Collection<Room> findVacantInTime(Date checkInDate, Date checkOutDate)
		{
			List<Room> vRooms = new ArrayList<>();
			
			boolean vacant;
			
			for(Room rm: rooms.values())
			{
				if(rm.getReservations().isEmpty())
					vRooms.add(rm);
				else
				{
					vacant = rm.getReservations().stream().filter(r -> !r.isCanceled())
																			 					.allMatch( r -> checkOutDate.before( r.getCheckInDate() ) || checkInDate.after( r.getCheckOutDate() ) );
					
					if(vacant)
						vRooms.add(rm);
				}
			}

			return vRooms;
		}
		
		public Room findRoom(Date checkInDate, Date checkOutDate, RoomType type)
		{
			Room room = null;
			
			for(Room rm: rooms.values())
			{
				if(rm.getRoomType() == type)
				{
					if(rm.getReservations().isEmpty())
					{
						room = rm;
						break;
					}
					else
					{
						boolean vacant;
						vacant = rm.getReservations().stream().filter(r -> !r.isCanceled())
																				 					.allMatch( r -> checkOutDate.before( r.getCheckInDate() ) || checkInDate.after( r.getCheckOutDate() ) );
						
						if(vacant)
						{
							room = rm;
							break;
						}
					}
				}
			}			
						
			return room;
		}
		
		public Collection<Reservation> getCustomerReservations(String email)
		{
			Stream<Reservation> reservations = rooms.values().stream().flatMap(rm -> rm.getReservations().stream());
					
			reservations = reservations.filter( r -> email.compareTo(r.getCustomer().getEmail()) == 0);
			
			return reservations.toList();
		}
		
		public Collection<Reservation> getCustomersReservations(Customer customer)
		{ 
			String email = customer.getEmail();
			
			return getCustomerReservations(email);
			
			//Stream<Reservation> reservations = rooms.values().stream().flatMap(rm -> rm.getReservations().stream());
					
			//reservations = reservations.filter( r -> email.compareTo(r.getCustomer().getEmail()) == 0);
	
			//return reservations.toList();
		}
		
		public Collection<Reservation> getRoomReservations(String roomID) {
			return rooms.get(roomID).getReservations();
		}

		public Collection<Reservation> getAllReservations() {
			return rooms.values().stream().flatMap(rm -> rm.getReservations().stream()).toList();
		}
		
		public Collection<Reservation> getNonCanceledReservations(String email)
		{
			Collection<Reservation> reservations = getCustomerReservations(email);
			return reservations.stream().filter(r -> !r.isCanceled()).toList();
		}
		
		public Optional<Reservation> getReservationByID(Collection<Reservation> reserves, int ID)
		{
			Optional<Reservation> RO = reserves.stream().filter( r -> r.getID() == ID).findAny();
			return RO;
		}
		
		public Optional<Reservation> cancelReservation(Collection<Reservation> reserves, int ID)
		{
			Optional<Reservation> RO = reserves.stream().filter( r -> r.getID() == ID).findFirst();
			
			if(RO.isPresent())
			{
				RO.get().cancel();
				
				hotelRepo.updateReservations(RO.get().getRoom());
					
				return RO;
			}
			else
				return Optional.empty();
		}
		
		public boolean changeRoom(String roomID, Reservation reserves)
		{
			RoomType oldType = reserves.getRoom().getRoomType();
			RoomType newType = rooms.get(roomID).getRoomType();
			
			if(oldType != newType)
				return false;
				
			if( rooms.get(roomID).getReservations().isEmpty() )
			{
				String oldRoomID = reserves.getRoom().getRoomNumber();
			
				rooms.get(oldRoomID).getReservations().remove(reserves);
			
				reserves.getRoom().setRoomNumber(roomID);
			
				rooms.get(roomID).getReservations().add(reserves);
			
				hotelRepo.updateReservations(rooms.get(oldRoomID));
				hotelRepo.updateReservations(rooms.get(roomID));
				
				return true;
			}
			
			Date cid = reserves.getCheckInDate();
			Date cod = reserves.getCheckOutDate();
			
			boolean vacant = rooms.get(roomID).getReservations().stream().filter(r -> !r.isCanceled())
																				 									.allMatch( r -> cod.before( r.getCheckInDate() ) || cid.after( r.getCheckOutDate() ) );
			
			if(!vacant)
				return false;
			
			String oldRoomID = reserves.getRoom().getRoomNumber();
			
			rooms.get(oldRoomID).getReservations().remove(reserves);
			
			reserves.getRoom().setRoomNumber(roomID);
			
			rooms.get(roomID).getReservations().add(reserves);
			
			hotelRepo.updateReservations(rooms.get(oldRoomID));
			hotelRepo.updateReservations(rooms.get(roomID));
			
			return true;
		}
}

