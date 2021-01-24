package jar;

import java.lang.reflect.*;
import java.util.*;

public class Inspector {

    private HashMap<String, String> printed;

    public void inspect(Object obj, boolean recursive) {
		printed = new HashMap<>();
		String ref = Integer.toHexString(obj.hashCode());
		printed.put(ref, ref);
		inspect(obj, recursive, 0);
    	
	}

	public void inspect(Object obj, boolean recursive, int depth){
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, depth);
	}

	
	private String tabs(int depth){
		String tab = new String(new char[depth]).replace('\0', '\t');
		return tab;
	}

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	String lead = new String(new char[depth]).replace('\0', '\t');
        System.out.print(lead);
        System.out.println("CLASS");
        System.out.print(lead);
        System.out.println(String.format("Class: %s", c.getName()));
    	
    	// if (c.isArray()) {
    	// 	inspectArray(c, obj, recursive, depth);
    	// 	return;
    	// }
    	
    	inspectFields(c, obj, recursive, depth);
    	
    }

    private void inspectFields(Class c, Object obj, boolean recursive, int depth){
        for(Field f: c.getDeclaredFields()){
            if(!Modifier.isStatic(f.getModifiers())){
                f.setAccessible(true);
                inspectField(f, obj, recursive, depth);
            }
        }
    }

	private void inspectField(Field f, Object obj, boolean recursive, int depth) {

        String lead = new String(new char[depth]).replace('\0', '\t');
        System.out.print(lead);
        System.out.println(String.format("  Field: %s", f));
		
				
		Class fType = f.getType();
    	f.setAccessible(true);
				
				Object fObj = null;
				
				try {
					fObj = f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				if(fObj == null) {
					System.out.print(lead);
					System.out.println(String.format("  Value: null"));
				}
				
				else if (fType.isArray()) {
					inspectArray(fType, fObj, recursive, depth);
					
				}
				
				else if(fType.isPrimitive()) {
                    System.out.print(lead);
					System.out.println(String.format("  Value: %s", fObj.toString()));
				}
				
				
				else {
                    System.out.print(lead);
                    System.out.println(String.format("  Value(ref): %s@%s", fObj.getClass().getName(), Integer.toHexString(System.identityHashCode(fObj)) ));
                    System.out.print(lead);
                    System.out.println(("  -> Recursively inspect"));
                    //System.out.printf("\n" + tabs(depth) + "  Value (ref): " + fObj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(fObj)));
					//System.out.printf("\n" + tabs(depth) + "  -> Recursively inspect\n");
					inspectClass(fObj.getClass(), fObj, recursive, depth+1);
					}
				
			
	}

	private void inspectArray(Class c, Object obj, boolean recursive, int depth) {	
		
        Class cType = c.getComponentType();
        String lead = new String(new char[depth]).replace('\0', '\t');
		
        int arrlength = Array.getLength(obj);
        System.out.print(lead);
        System.out.println(String.format("  Type: array"));
        System.out.print(lead);
        System.out.println(String.format("  Length: %s",Integer.toString(arrlength)));

		
		if(arrlength > 0) {
            System.out.print(lead);
            System.out.println(String.format("  Entries->"));
		}
		
		for(int i = 0; i < arrlength; i++) {
			Object aObj = Array.get(obj, i);
			
			if (aObj == null) {
                System.out.print(lead);
                System.out.println(String.format("   Value: null"));
		}
			else if(cType.isArray()) {
                System.out.print(lead);
                System.out.println(String.format("   Value(ref): %s", aObj.toString()));
				inspectArray(aObj.getClass(), aObj, recursive, depth);
			}
			
			else if(cType.isPrimitive()) {
                System.out.print(lead);
                System.out.println(String.format("   Value: %s", aObj.toString()));
				
			}
			else {
				System.out.print(lead);
                System.out.println(String.format("   Value(ref): %s@%s", aObj.getClass().getName(), Integer.toHexString(System.identityHashCode(aObj)) ));
                System.out.print(lead);
                System.out.println(("   -> Recursively inspect"));
				inspectClass(aObj.getClass(), aObj, recursive, depth+1);
			}
			
		}
		
		
		
	}
    

}
