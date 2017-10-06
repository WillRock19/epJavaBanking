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
    	
    	try 
    	{
    		WebServer server = new WebServer(6789);
        	server.Execute();	
    	}
    	catch(Exception e) 
    	{
    		System.err.println("Um erro ocorreu ao executar o servidor solicitado.");
    		System.exit(0);
    	}
    	
    	
    	
    	//TODO: adicionar verificacao se usuario quer criar um servidor ou um cliente e chamar metodos especificos
    	
    	
    	
//        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
  //      System.out.println(Message.fromJSON(json));
    }
}
