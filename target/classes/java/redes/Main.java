package redes;


import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
        System.out.println(Message.fromJSON(json));
    }
}
