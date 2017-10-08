package redes;


import com.google.gson.Gson;
import redes.clientSide.ClientServer;
import redes.serverSide.WebServer;

public class Main 
{
    public static void main(String[] args) 
    {
    /*	if(args.length == 0)
    	{
    		System.err.println("Favor, insira o parametro quando for executar a aplicacao.");
        	System.exit(0);
    	}*/
    	
    	//TODO: melhorar logica. Deve receber porta do cliente? 
    	
    	if(args[0] == "cliente") 
    	{
    		executeAsClient();
    	}
    	else {
    		int portNumber = 6789;
    		executeAsServer(portNumber);
    	}
    		
//        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
  //      System.out.println(Message.fromJSON(json));
    }
    
    private static void executeAsServer(int portNumber) 
    {
    	try 
    	{
    		WebServer server = new WebServer(portNumber);
        	server.Execute();	
    	}
    	catch(Exception e) 
    	{
    		System.err.println("Um erro ocorreu ao executar o programa em modo SERVIDOR.");
    		System.exit(0);
    	}
    }
    
    private static void executeAsClient() 
    {
    	try 
    	{
    		ClientServer server = new ClientServer("localhost", 0);
        	server.Execute();	
    	}
    	catch(Exception e) 
    	{
    		System.err.println("Um erro ocorreu ao executar o programa em modo CLIENTE.");
    		System.exit(0);
    	}
    }
}
