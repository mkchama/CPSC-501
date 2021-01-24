package jar;

//prettyPrintString and singleLineString implemented from Tutorial
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.json.*;
import javax.json.stream.*;

public class ObjectCreator {

    public static void printMenu() {
        System.out.println("Welcome: choose from the following options");
        System.out.println("  1: Create ObjectA: ");
        System.out.println("  2: Create ObjectB: ");
        System.out.println("  3: Create ObjectC: ");
        System.out.println("  4: Create ObjectD: ");
        System.out.println("  5: Create ObjectE: ");
        System.out.println("  6: Exit");
    }


    private static int getMenuOption() {
        Scanner input = new Scanner(System.in);
        while(true){
            printMenu();
            int choice = Integer.parseInt(input.nextLine());
            switch(choice){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return choice;
                default:
                    System.out.println("Enter a value from 1-6");
            }
            input.close();
        }
       
    }

    private static Object createObject(int choice){
        Object obj = null;
        switch(choice){
            case 1:
                obj = createObjectA();
                break;
            case 2:
                obj = createObjectB();
                break;
            case 3:
                obj = createObjectC();
                break;
            case 4:
                obj = createObjectD();
                break;
            case 5:
                obj = createObjectE();
                break;
            case 6:
                System.out.println("Exiting program. Goodbye");
                System.exit(0);
                break;
        }
        return obj;
    }

    private static Object createObjectA(){
        System.out.println("To create ObjectA, specify an int value and a double value");
        System.out.println("If you do not set the values, they will be defaulted to 0");
        ObjectA a = new ObjectA();
        Scanner input = new Scanner(System.in);
        int i = 0;
        double d =0.0;
        while (true){
            System.out.println("Int value: ");
            String value = input.nextLine();
            try{
                i = Integer.parseInt(value);
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid integer value.");
            }
        }
        while(true){
            System.out.println("Double value: ");
            String value = input.nextLine();
            try{
                d = Double.parseDouble(value);
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid double value.");
            }
        }
        a.x = i;
        a.y = d;
   
        return a;
    }

    private static Object createObjectB(){
        System.out.println("This object demonstrates circular reference to ObjectA.");
        ObjectB b = new ObjectB();
        b.a_new = (ObjectA) createObjectA();
        ObjectA a = new ObjectA();
        a.a_a = b.a_new;
        //Scanner input = new Scanner(System.in);
        return a;
    }

    private static Object createObjectC(){

        Scanner input = new Scanner(System.in);
        ObjectC c = new ObjectC();

        System.out.print("Define a length for the array: ");
        int value = Integer.parseInt(input.nextLine());
        int [] array1 = new int[value];
        
        System.out.println("Enter in values for the array: \n");
        for(int i = 0; i < value; i++){
            System.out.print("["+ i +"]: ");
            try{
                array1[i] = Integer.parseInt(input.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid int value. Value will be defaulted to 0");
            }
        }
        input.close();
        c = new ObjectC(array1);
        return c;

    }

    private static Object createObjectD(){
        System.out.println("To create a reference array, please specify a length for the array");
        Scanner input = new Scanner(System.in);
        String value = input.nextLine();
        int len = Integer.parseInt(value);
        Object[] array = new Object[len];
        for(int i = 0; i < len; i++){
            System.out.println("Enter either 'null' for no object or 'new' for a reference object");
            String choice = input.nextLine();

            if(choice.equals("new")){
                System.out.println("Creating a new object at index ["+i+"]");
                array[i] = (ObjectA) createObjectA(); 
            }
            else if(choice.equals("null")){
                System.out.println("Inserting null at index ["+i+"]");
                array[i] = null;
            }
            else {
                System.out.println("You have entered an invalid input. Defaulting to null");
            }
        }
        input.close();
        ObjectD d = new ObjectD(array);
        return d;
        
    }

    private static Object createObjectE(){
        System.out.println("To create an ArrayList reference, please specify a length for the array");
        Scanner input = new Scanner(System.in);
        String value = input.nextLine();
        int len = Integer.parseInt(value);
        ArrayList<Object> arrayList = new ArrayList<>(len);
        for(int i = 0; i < len; i++){
            System.out.println("Enter either 'null' or 'new' for a reference object");
            String choice = input.nextLine();

            if(choice.equals("new")){
                System.out.println("Creating a new object at index ["+i+"]");
                arrayList.add((ObjectA) createObjectA());
            }
            else if(choice.equals("null")){
                arrayList.add(null);
            }
            else {
                System.out.println("You have entered an invalid input. Defaulting to null");
            }
        }
        ObjectE e = new ObjectE(arrayList);
        return e;
        
    }

    public static Object ObjectSender(){
        Object obj = createObject(getMenuOption());
        return obj;

    }

    public static String singleLineString(Object o) throws Exception{
        JsonObject json_object = Serializer.serializeObject(o);
        return json_object.toString();
    }

    public static String prettyPrintString(Object o) throws Exception{
        JsonObject json_object = Serializer.serializeObject(o);
        StringWriter string_writer = new StringWriter();
        Map<String, Object> settings_map = new HashMap<>();
        settings_map.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory write_factory = Json.createWriterFactory(settings_map);
        JsonWriter json_writer = write_factory.createWriter(string_writer);
        json_writer.writeObject(json_object);
        json_writer.close();
        String prettyPrint = string_writer.toString();
        return prettyPrint;
    }

}