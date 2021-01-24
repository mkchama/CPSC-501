package jar;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.json.*;
import javax.json.stream.*;

public class ObjectCreatorTest {

    public static void main(String[] args) throws IOException, Exception {

        ObjectA a = new ObjectA();

        a.x = 1;
        a.y = 2.2;

        FileWriter fw = new FileWriter("ObjectA.json");
        fw.write(ObjectCreator.prettyPrintString(a));
        fw.close();

        ObjectB b = new ObjectB();
        
        b.a_new.x = 3;
        b.a_new.y = 4.4;

        a.a_a = b.a_new;

        FileWriter fw1 = new FileWriter("ObjectB.json");
        fw1.write(ObjectCreator.prettyPrintString(a));
        fw1.close();



    }


    
}