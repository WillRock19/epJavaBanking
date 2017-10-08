package redes.messages;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerResponseMessage  implements Serializable
{
	private boolean authenticationSucceded;
	
	public ServerResponseMessage CreateServerAuthenticationError() 
	{
		authenticationSucceded = false;
		return this;
	}
	
	public ServerResponseMessage CreateServerAuthenticationSuccess() 
	{
		authenticationSucceded = true;
		return this;
	}
	
	 public String toJson()
	 {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
	
	public static ServerResponseMessage fromJSON(String json)
	{
        Gson gson = new Gson();
        return gson.fromJson( json, ServerResponseMessage.class );
    }
}
