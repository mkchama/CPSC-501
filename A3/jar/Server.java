package jar;

import java.net.*;
import java.util.*;

import javax.json.JsonObject;

import java.io.*;

public class Server {

    public static void main(String args[]) throws Exception
    {
        int port = 7500;
        Socket socket = new Socket("localhost", port);
        String outgoingJsonFile = "SerializedObject.json";
        System.out.println("This is the server interface:");
        System.out.println("--------------------------------");
            System.out.println("Create objects that will be serialized and send them to the client");
            System.out.println("--------------------------------");
            Object obj = ObjectCreator.ObjectSender();
            System.out.println("--------------------------------");
            FileWriter fw2 = new FileWriter(outgoingJsonFile);
            fw2.write(ObjectCreator.prettyPrintString(obj));
            fw2.close();
            JsonObject json_object = Serializer.serializeObject(obj); 

            OutputStream outputStream = socket.getOutputStream();
        
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(((Object) json_object).toString());
            oos.flush();
            socket.close();

            System.out.println("Serializing the object and sending it over to the client");
            System.out.println("--------------------------------");
   
    
    }


    
}