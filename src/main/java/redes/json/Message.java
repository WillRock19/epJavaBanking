package redes.json;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redes.ActionMessage;

public class Message implements Serializable 
{
	private static final long serialVersionUID = -607006480252904552L;

	public String toJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

	public static Message fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, ActionMessage.class);
	}
	
	public static AuthenticationResponseMessage authenticationFromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, AuthenticationResponseMessage.class);
	}
}