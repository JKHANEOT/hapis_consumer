package com.hapis.customer.networking.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.hapis.customer.exception.HapisException;

import java.lang.reflect.Type;
import java.util.Date;

public class JSONAdaptor {
	private static Gson gson;

	public static void register() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.enableComplexMapKeySerialization().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gson = gsonBuilder.disableHtmlEscaping().create();
	}

	public static Gson getGson() {
		return gson;
	}

	public static <T> String toJSON(T t) throws HapisException {
		return gson.toJson(t, t.getClass());
	}

	public static <T> T fromJSON(String jsonStr, Class<T> c) throws JsonSyntaxException, HapisException {
		return gson.fromJson(jsonStr, c);
	}

	private static class DateSerializer implements JsonSerializer<Date> {
		@Override
		public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(date.getTime());
		}
	}

	private static class DateDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement ele, Type type, JsonDeserializationContext context) throws JsonParseException {
			String dateStr = ele.getAsString();
			if (dateStr == null || dateStr.trim().length() == 0) {
				return null;
			}
			return new Date(Long.parseLong(dateStr));
		}
	}
}
