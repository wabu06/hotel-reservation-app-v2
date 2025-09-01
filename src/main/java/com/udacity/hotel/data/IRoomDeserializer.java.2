package com.udacity.hotel.data;


import com.google.gson.*;

import java.lang.reflect.Type;

import com.udacity.hotel.models.*;


public class IRoomDeserializer implements JsonDeserializer<IRoom>
{
	@Override
	public IRoom deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject jsonObject = element.getAsJsonObject();
		
		String roomNumber = jsonObject.get("roomNumber").getAsString();
		Double price = jsonObject.get("price").getAsDouble();
		
		String tstr = jsonObject.get("type").getAsString();
		RoomType type;
		
		if(tstr.compareTo("DOUBLE") == 0)
			type = RoomType.DOUBLE;
		else
			type = RoomType.SINGLE;
		
		if(price == 0.0)
			return new FreeRoom(roomNumber, type);
		else
			return new Room(roomNumber, price, type);
	}
}
