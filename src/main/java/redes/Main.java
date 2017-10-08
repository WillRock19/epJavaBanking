package redes;


import java.net.InetAddress;


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
    	
    	int portNumber = 6789;
    	
    	if(args.length != 0 && args[0].equals("cliente")) 
    	{
    		executeAsClient(args[1], 6789);
    	}
    	else {
    		executeAsServer(portNumber);
    	}
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
    
    private static void executeAsClient(String serverIP, int portNumber) 
    {
    	try 
    	{
    		ClientServer server = new ClientServer(serverIP, portNumber);
        	server.Execute();	
    	}
    	catch(Exception e) 
    	{
    		System.err.println("Um erro ocorreu ao executar o programa em modo CLIENTE.");
    		System.exit(0);
    	}
    }
}
