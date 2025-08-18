package com.udacity.hotel.models;


import java.util.*;

public class FreeRoom extends Room
{
		public FreeRoom(String N, RoomType T) {
			super(N, 0.0, T);
		}
		
		public FreeRoom(FreeRoom rm) {
			super(rm.roomNumber, 0.0, rm.type);
			RoomReservations = null;
		}
	
		@Override 
		public String toString()
		{
			String RmStr = "Room Number: " + super.roomNumber + "\nPrice: $" + super.price + "\nRoom Type: ";
		
			if (super.type ==  RoomType.SINGLE)
				RmStr += "SINGLE";
			else
				RmStr += "DOUBLE";
		
			return RmStr;
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof FreeRoom) )
				return false;
		
			FreeRoom R = (FreeRoom) O;
			
			return roomNumber.equals(R.roomNumber) && price.equals(R.price) && (type == R.type) && RoomReservations.equals(R.RoomReservations);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(roomNumber, price, type, RoomReservations); }
}
