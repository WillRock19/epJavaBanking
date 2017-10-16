package redes.json;

import java.io.Serializable;
import java.lang.reflect.Type;

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
		return (AuthenticationResponseMessage) messageFromJson(json, AuthenticationResponseMessage.class);
	}

	public static DepositResponseMessage depositFromJSON(String json){
		return (DepositResponseMessage) messageFromJson(json, DepositResponseMessage.class);
	}

	public static WithdrawResponseMessage WithDrawlResponseMessageFromJson(String json) {
		return (WithdrawResponseMessage) messageFromJson(json, WithdrawResponseMessage.class);
	}

	public static FinancialTransactionsResponseMessage FinancialTransactionsResponseMessageFromJson(String json) {
		return (FinancialTransactionsResponseMessage) messageFromJson(json,FinancialTransactionsResponseMessage.class);
	}

	private static Message messageFromJson(String json, Class<?> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, (Type) clazz);
	}
}