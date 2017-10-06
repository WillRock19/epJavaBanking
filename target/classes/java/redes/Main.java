package redes;


import com.google.gson.Gson;
import redes.clientSide.ClientServer;

public class Main 
{
    public static void main(String[] args) 
    {
    	
    	//TODO: adicionar verificacao se usuario quer criar um servidor ou um cliente e chamar metodos especificos
    	
    	
    	
        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
        System.out.println(Message.fromJSON(json));
    }
}
