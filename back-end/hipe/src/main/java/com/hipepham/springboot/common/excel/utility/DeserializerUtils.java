package com.hipepham.springboot.common.excel.utility;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class DeserializerUtils {

	public static JsonDeserializer<Date> getDateDeserializer() {
		return new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return json == null ? null : DateUtils.stringToDate(json.getAsString(), "EEE MMM dd HH:mm:ss Z yyyy");
			}
		};
	}
}
