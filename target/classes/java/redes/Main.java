package redes;


import com.google.gson.Gson;
import redes.clientSide.ClientServer;

public class Main 
{
    public static void main(String[] args) 
    {
    	
    	if(args[0].toLowerCase() == "c") 
    	{
    		ClientServer cliente = new ClientServer();
    		cliente.ExecuteTcpConnection();
    	}
    	else if(args[0].toLowerCase() == "s") 
    	{
    		
    	}
    	
    	
    	
        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
        System.out.println(Message.fromJSON(json));
    }
}
