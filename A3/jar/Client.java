package jar;

import java.net.*;
import java.util.*;

import javax.json.*;

import java.io.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("This is the client interface:");
        System.out.println("--------------------------------");
        try{
            ObjectInputStream is;
            ServerSocket ss = new ServerSocket(7500);
            Socket socket = ss.accept();
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            //Json newObject = (Json)objectInputStream.readObject();
            System.out.println("Received object. Attempting to deserialize the object.");
            System.out.println("--------------------------------");
            Inspector inspector = new Inspector();

            String json_string = (String) objectInputStream.readObject();

            JsonReader json_reader = Json.createReader(new StringReader(json_string));
            JsonObject json_object = json_reader.readObject();

            System.out.println("Printing off the inspection of the deserialized object.");
            System.out.println("--------------------------------");

            Object object = Deserializer.deserializeObject(json_object);
            inspector.inspect(object, true);

        }
        catch(IOException e){
            System.out.println("Could not connect. Exiting.");
        }
    }
    
}