package com.example.guojing.my_minresume.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by AmazingLu on 8/29/17.
 */

public class ModelUtil {

    /**
     * create a new Gson object for serialization and deserialization
     * Gson do not konw how to serialize Uri
     * so we have to tell Gson how to do it
     * */
    private static Gson gsonForSerialization = new GsonBuilder().
            registerTypeAdapter(Uri.class, new UriSerializer()).create();
    private static Gson gsonForDeserialization = new GsonBuilder().
            registerTypeAdapter(Uri.class, new UriDeseralizer()).create();

    private static String PREF_NAME = "models";

    public static void save(Context context, String key, Object object) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        // use Gson to serialize
        String jsonString = gsonForSerialization.toJson(object);
        // put the key and value into SharedPreferences
        sp.edit().putString(key, jsonString).apply();
    }

    public static <T> T read(Context context, String key, TypeToken<T> typeToken) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        try {
            // get the value base on key in SharedPreferences and deserialize using Gson
            return gsonForDeserialization.fromJson(sp.getString(key, ""), typeToken.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class UriSerializer implements JsonSerializer<Uri> {
        @Override
        public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    private static class UriDeseralizer implements JsonDeserializer<Uri> {
        @Override
        public Uri deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Uri.parse(json.getAsString());
        }
    }
}
