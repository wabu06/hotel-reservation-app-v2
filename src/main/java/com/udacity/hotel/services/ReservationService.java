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
		
		Map< String, ArrayList<Reservation> > singles;
		Map< String, ArrayList<Reservation> > doubles;
		
		PretendDataBaseHotelRepo hotelRepo;
		
		final static ReservationService instance = new ReservationService();
		
		public static ReservationService getInstance() { return instance; }
		
		private ReservationService()
		{ 
			hotelRepo = PretendDataBaseHotelRepo.getInstance();
			
			rooms = hotelRepo.getRooms();
			singles = hotelRepo.getSingles();
			doubles = hotelRepo.getDoubles();
			
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
			
			if(singles.get(roomID) != null)
				singles.get(roomID).remove(R);
			else
				doubles.get(roomID).remove(R);
			
			return R;
		}
		
		public Reservation restoreReservation(Reservation R)
		{
			String roomID = R.getRoom().getRoomNumber();
			RoomType type = R.getRoom().getRoomType();
			
			if(type == RoomType.SINGLE)
			{
				singles.get(roomID).add(R);
			}
			else
				doubles.get(roomID).add(R);
			
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
			
			if(room.getRoomType() == RoomType.SINGLE)
			{
				singles.get(roomID).add(R);
				hotelRepo.updateSingles(singles);
			}
				
			else
			{
				doubles.get(roomID).add(R);
				hotelRepo.updateDoubles(doubles);
			}

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
			
			String roomID = room.getRoomNumber();
			RoomType type = room.getRoomType();
			
			String email = customer.getEmail();
			
			if(type == RoomType.SINGLE)
			{
				singles.get(roomID).add(R);
				hotelRepo.updateSingles(singles);
			}
			else
			{
				doubles.get(roomID).add(R);
				hotelRepo.updateDoubles(doubles);
			}

			return R;
		}
		
		public Room findRoom(Date checkInDate, Date checkOutDate, RoomType type)
		{
			Room room = null;
			
			if(type == RoomType.SINGLE)
			{
				boolean vacant;
				
				for(Map.Entry< String, ArrayList<Reservation> > E: singles.entrySet())
				{
					if(E.getValue().isEmpty())
					{
						room = rooms.get(E.getKey());
						break;
					}
					else
					{
						vacant = E.getValue().stream().filter(r -> !r.isCanceled())
																					.allMatch( r -> checkOutDate.before( r.getCheckInDate() ) || checkInDate.after( r.getCheckOutDate() ) );
						
						if(vacant)
						{
							room = rooms.get(E.getKey());
							break;
						}
					}
				}
			}
			else
			{
				boolean vacant;
				
				for(Map.Entry< String, ArrayList<Reservation> > E: doubles.entrySet())
				{
					if(E.getValue().isEmpty())
					{
						room = rooms.get(E.getKey());
						break;
					}
					else
					{
						vacant = E.getValue().stream().filter(r -> !r.isCanceled())
																					.allMatch( r -> checkOutDate.before( r.getCheckInDate() ) || checkInDate.after( r.getCheckOutDate() ) );
						
						if(vacant)
						{
							room = rooms.get(E.getKey());
							break;
						}
					}
				}
			}
			
			return room;
		}
		
		public Collection<Reservation> getCustomerReservations(String email)
		{
			Stream<Reservation> reservations = Stream.concat(singles.values().stream().flatMap(r -> r.stream()), doubles.values().stream().flatMap(r -> r.stream()));
			
			reservations = reservations.filter( r -> email.compareTo(r.getCustomer().getEmail()) == 0);
			
			//reservations = reservations.filter(r -> !r.isCanceled());
			
			return reservations.toList();
		}
		
		public Collection<Reservation> getCustomersReservations(Customer customer)
		{ 
			String email = customer.getEmail();
			
			Stream<Reservation> reservations = Stream.concat(singles.values().stream().flatMap(r -> r.stream()), doubles.values().stream().flatMap(r -> r.stream()));
			
			reservations = reservations.filter( r -> email.compareTo(r.getCustomer().getEmail()) == 0);
			
			//reservations = reservations.filter(r -> !r.isCanceled());
	
			return reservations.toList();
		}
		
		public Collection<Reservation> getRoomReservations(String roomID)
		{
			Room room = rooms.get(roomID);
			
			if(room.getRoomType() == RoomType.SINGLE)
				return singles.get(roomID);
			else
				return doubles.get(roomID);
		}

		public Collection<Reservation> getAllReservations()
		{
			//return ReservationMap.values().stream().flatMap( a -> a.stream() ).collect(Collectors.toList());
			
			Stream<Reservation> reservations = Stream.concat(singles.values().stream().flatMap(r -> r.stream()), doubles.values().stream().flatMap(r -> r.stream()));
			return reservations.toList();
		}
		
		public Collection<Reservation> getNonCanceledReservations(String email)
		{
			Collection<Reservation> reservations = getCustomerReservations(email);
			return reservations.stream().filter(r -> !r.isCanceled()).toList();
			
				//return ReservationMap.get(email).stream().filter(r -> !r.isCanceled()).toList();
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
				
				if(RO.get().getRoom().getRoomType() == RoomType.SINGLE)
					hotelRepo.updateSingles(singles);
				else
					hotelRepo.updateDoubles(doubles);
					
				return RO;
			}
			else
				return Optional.empty();
		}
}

