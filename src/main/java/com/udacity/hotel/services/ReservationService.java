package com.udacity.hotel.services;


import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;


import com.udacity.hotel.models.*;


public class ReservationService
{
	 	Map<String, IRoom> RoomMap;
		Map< String, ArrayList<Reservation> > ReservationMap;
		
		final static ReservationService instance = new ReservationService();
		
		public static ReservationService getInstance() { return instance; }
		
		private ReservationService()
		{ 
			RoomMap = new HashMap<String, IRoom>();
			ReservationMap = new HashMap< String, ArrayList<Reservation> >();
		}
		
		public void addRoom(IRoom room) { RoomMap.put(room.getRoomNumber(), room); }
		
		public IRoom getARoom(String roomId) { return RoomMap.get(roomId); }
		
		public boolean roomExist(String roomId) { return RoomMap.containsKey(roomId); }
		
		public Collection<IRoom> getRooms() {
			return RoomMap.values();
		}
		
		public int getRoomCount() {
			return RoomMap.size();
		}
		
		public Reservation removeReservationFromRoom(Reservation R)
		{
			Collection<Reservation> rmReservations = R.getRoom().getReservations();
			
			if(!rmReservations.remove(R))
				throw new IllegalArgumentException("reservation not found");
			
			return R;
		}
		
		public Reservation restoreReservationToRoom(Reservation R) {
			R.getRoom().addReservation(R);
			return R;
		}
		
		public Reservation changeReservationRoom(Reservation R, String rm, Date cid, Date cod)
		{
			long diff = cod.getTime() - cid.getTime();
			
			long staylength = TimeUnit.MILLISECONDS.toDays(diff) + 1;
			
			IRoom room = RoomMap.get(rm);
			
			R.setRoom(room);
			
			Double totalCost = staylength * room.getRoomPrice();
			
			R.setStayLength(staylength);
			R.setTotal(totalCost);
			
			R.setCheckInDate(cid);
			R.setCheckOutDate(cod);
			
			room.addReservation(R);
				
			return R;
		}
		
		public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
		{
			// first check to see if room is available for the given dates
			
			long diff = checkOutDate.getTime() - checkInDate.getTime();
			
			long staylength = TimeUnit.MILLISECONDS.toDays(diff) + 1;
			
			Double totalCost = staylength * room.getRoomPrice();
			
			Reservation R = new Reservation(customer, new Room( (Room) room ), staylength, totalCost, checkInDate, checkOutDate);
			
			//RoomMap.get( room.getRoomNumber() ).addReservation(R);
			
			room.addReservation(R);
			
			String email = customer.getEmail();
			
			if( ReservationMap.containsKey(email) )
				{ ReservationMap.get(email).add(R); }
			else
			{
				ReservationMap.put( email, new ArrayList<Reservation>() );
				ReservationMap.get(email).add(R);
			}

			return R;
		}
		
		public HashMap<String, IRoom> findRooms(Date checkInDate, Date checkOutDate)
		{
			HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
			
			Date cid, cod;
			
			for(IRoom rm: RoomMap.values() )
			{
				if ( rm.hasReservations() )
				{
					boolean vacant = rm.getReservations().stream().filter(r -> !r.isCanceled())
																												.allMatch( r -> checkOutDate.before( r.getCheckInDate() ) || checkInDate.after( r.getCheckOutDate() ) );
					
					if(vacant)
						rooms.put( rm.getRoomNumber(), rm );
				}
				else
					rooms.put( rm.getRoomNumber(), rm );
			}
			
			return rooms;
		}
		
		public Collection<Reservation> getCustomerReservations(String email) {
			return ReservationMap.get(email);
		}
		
		public Collection<Reservation> getCustomersReservation(Customer customer)
			{ return ReservationMap.get( customer.getEmail() ); }
		
		Collection<Reservation> getRoomReservations(IRoom room)
			{ return room.getReservations(); }

		public Collection<Reservation> getAllReservations() {
			return ReservationMap.values().stream().flatMap( a -> a.stream() ).collect(Collectors.toList());
		}
		
		public Collection<Reservation> getNonCanceledReservations(String email)
		{
			if( ReservationMap.get(email) == null )
				return List.of();
			else	
				return ReservationMap.get(email).stream().filter(r -> !r.isCanceled()).toList();
		}
		
		public Optional<Reservation> getReservationByID(String email, int ID)
		{
			Optional<Reservation> RO = ReservationMap.get(email).stream().filter( r -> r.getID() == ID).findFirst();
			return RO;
		}
		
		public Optional<Reservation> cancelReservation(Collection<Reservation> reserves, int ID)
		{
			Optional<Reservation> RO = reserves.stream().filter( r -> r.getID() == ID).findFirst();
			
			if(RO.isPresent()) {
				RO.get().cancel();
				return RO;
			}
			else
				return Optional.empty();
		}
}

