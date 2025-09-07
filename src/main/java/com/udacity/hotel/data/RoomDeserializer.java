package com.udacity.hotel.data;


import com.google.gson.*;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import com.udacity.hotel.models.*;


public class RoomDeserializer implements JsonDeserializer<Room>
{
	@Override
	public Room deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject jsonObject = element.getAsJsonObject();
		
		String roomNumber = jsonObject.get("roomNumber").getAsString();
		
		String tstr = jsonObject.get("type").getAsString();
		RoomType type;
		
		if(tstr.compareTo("DOUBLE") == 0)
			type = RoomType.DOUBLE;
		else
			type = RoomType.SINGLE;
	
		Room room = new Room(roomNumber, type);
		
		Type listType = new TypeToken< List<Reservation> >() {}.getType();
		
		room.setReservations( context.deserialize(jsonObject.get("reservations"), listType) );
		
		return room;
	}
}
