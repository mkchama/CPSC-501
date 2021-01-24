import java.lang.reflect.*;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Mohammad Chama
 */
public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    	
	}

	private void errorCheck(Object obj, Class c){
		if (!c.isAssignableFrom(obj.getClass()) || obj == null) {
    		System.out.println("\t Error");
    		return;
    	}
	}
	
	private String tabs(int depth){
		String tab = new String(new char[depth]).replace('\0', '\t');
		return tab;
	}

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	
    	errorCheck(obj, c);
    	
		System.out.printf("" + tabs(depth) + "CLASS", depth);
		System.out.printf("\n" + tabs(depth) + "Class: " + c.getName(), depth);
    	
    	if (c.isArray()) {
    		inspectArray(c, obj, recursive, depth);
    		return;
    	}

    	inspectSuperClass(c, obj, recursive, depth);
    	
    	inspectInterfaces(c, obj, recursive, depth);
    	
    	inspectConstructors(c, obj, depth);
    	
    	inspectMethods(c, obj, depth);
    	
    	inspectFields(c, obj, recursive, depth);
    	
    }
    
    private void inspectSuperClass(Class c, Object obj, boolean recursive, int depth) {
		errorCheck(obj, c);
		

		
		Class superClass = c.getSuperclass();
		if(superClass != null) {
			System.out.printf( "\n" +tabs(depth)+ "SUPERCLASS -> Recursively Inspect\n" + tabs(depth) + "SuperClass: " + superClass.getName() +"\n", depth);
			inspectClass(superClass, obj, recursive, depth+1);
		}
		else {
			System.out.printf("\n" + tabs(depth) + "SuperClass: NONE", depth);
		}
		
	}

	private void inspectMethods(Class c, Object obj, int depth) {

		errorCheck(obj, c);
		
		System.out.printf("\n" + tabs(depth) + "METHODS ( " +c.getName() +" )\n"+ tabs(depth) + "Methods->", depth);
		
	
		
		if(c.getDeclaredMethods().length > 0) {
			
			for(Method m: c.getDeclaredMethods()) {
				System.out.printf("\n" + tabs(depth) + " METHOD\n" + tabs(depth) + "  Name: "+ m.getName(), depth);
				
				if(m.getExceptionTypes().length >0) {
					System.out.printf("\n" + tabs(depth) + "  Exceptions->");
					for(Class ex : m.getExceptionTypes()) {
						System.out.printf("\n" + tabs(depth) + "  "+ ex, depth);
					}
				}
				
				else {
					System.out.printf("\n" + tabs(depth) + "  Exceptions-> NONE");
				}
			
				
				
				if(m.getParameterTypes().length > 0) {
					System.out.printf("\n" + tabs(depth) + "  Parameter types->");
					for(Parameter param : m.getParameters()) {
						System.out.printf("\n" + tabs(depth) + "  " + param.getType(), depth);
					}
				}
				else {
					System.out.printf("\n" + tabs(depth) + "  Parameter types -> NONE");
				}
				
				
				
				System.out.printf("\n" + tabs(depth) + "  Return type: " + m.getReturnType().getName(), depth);
				int mod = m.getModifiers();
				if(m.getModifiers() > 0){
					System.out.printf("\n" + tabs(depth) + "  Modifiers: " + Modifier.toString(mod), depth);
				}
				else{
					System.out.printf("\n" + tabs(depth) + "  Modifiers: NONE", depth);
				}
			}
		}

		else {
			System.out.printf("\n" + tabs(depth) + "  Methods-> NONE", depth);
			
		}
		
	}

	private void inspectFields(Class c, Object obj, boolean recursive, int depth) {
		errorCheck(obj, c);
		
		System.out.printf("\n" + tabs(depth) + "FIELDS ( " +c.getName() + " )\n" + tabs(depth) + "Fields->", depth);
		
		
		if(c.getDeclaredFields().length > 0) {

			for(Field f : c.getDeclaredFields()) {
				System.out.printf("\n" + tabs(depth) + " FIELD\n" + tabs(depth) + "  Name: "+ f.getName(), depth);
				
				Class fType = f.getType();
				System.out.printf("\n" + tabs(depth) + "  Type: " +fType.getName(), depth);
				
				int mod = f.getModifiers();
				if(f.getModifiers() > 0){
					System.out.printf("\n" + tabs(depth) + "  Modifiers: " + Modifier.toString(mod), depth);
				}
				else{
					System.out.printf("\n" + tabs(depth) + "  Modifiers: NONE", depth);
				}

				
				f.setAccessible(true);
				
				Object fObj = null;
				
				try {
					fObj = f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				if(fObj == null) {
					System.out.printf("\n" + tabs(depth) + "  Value: null", depth);
				}
				
				else if (fType.isArray()) {
					inspectArray(fType, fObj, recursive, depth);
					
				}
				
				else if(fType.isPrimitive()) {
					System.out.printf("\n" + tabs(depth) + "  Value: " + fObj.toString(), depth);
				}
				
				else if(!recursive) {
					System.out.printf("\n" + tabs(depth) + "  Value (ref): " + fType.getName() + "@" + Integer.toHexString(System.identityHashCode(fObj)));
				}
				
				else {
					System.out.printf("\n" + tabs(depth) + "  Value (ref): " + fObj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(fObj)));
					System.out.printf("\n" + tabs(depth) + "  -> Recursively inspect\n");
					inspectClass(fObj.getClass(), fObj, recursive, depth+1);
					}
				}
		}
		else {
			System.out.printf(" NONE", depth);
		}
			
	}

	private void inspectConstructors(Class c, Object obj, int depth) {
		errorCheck(obj, c);

		System.out.printf("\n" + tabs(depth) + "CONSTRUCTORS ( " +c.getName() +" )\n"+ tabs(depth) + "Constructors->", depth);
		
		Constructor[] con = c.getDeclaredConstructors();
		
		if(con.length > 0) {
			for(Constructor co : con) {
				System.out.printf("\n" + tabs(depth) + " CONSTRUCTOR\n" + tabs(depth) + "  Name: "+ co.getName(), depth);
				

				if(co.getExceptionTypes().length > 0){
					for(Class ex : co.getExceptionTypes()) {
						System.out.printf("\n" + tabs(depth) + "  Exceptions->\n" + tabs(depth) + "  "+ ex);
					}
				}
				else{
					System.out.printf("\n" + tabs(depth) + "  Exceptions-> NONE");
				}
				
				
				if(co.getParameterTypes().length > 0){
					System.out.printf("\n" + tabs(depth) + "  Parameter types->");
					for(Parameter pam: co.getParameters()) {
						System.out.printf("\n" + tabs(depth) + "  " + pam.getParameterizedType(), depth);
					}

				}
				else{
					System.out.printf("\n" + tabs(depth) + "  Parameter types: NONE");
				}
				
				
				int mod = co.getModifiers();
				if(mod > 0){
					System.out.printf("\n" + tabs(depth) + "  Modifiers: " + Modifier.toString(mod), depth);
				}
				else{
					System.out.printf("\n" + tabs(depth) + "  Modifiers: NONE", depth);
				}
				
			}
			
		}
		
		else {
			System.out.printf(" NONE", depth);	
		}
		
	}

	private void inspectInterfaces(Class c, Object obj, boolean recursive, int depth) {
		errorCheck(obj, c);
		
		Class[] face = c.getInterfaces();
		System.out.printf("\n" + tabs(depth) + "INTERFACES ( " +c.getName() +" )\n" + tabs(depth) + "Interfaces->", depth);
		
		if(face.length >0) {
			for (Class f : face) {
				System.out.printf("\n" + tabs(depth) + " INTERFACE -> Recursively Inspect\n" + tabs(depth) + " "+ f.getName() +"\n", depth);
				inspectClass(f, obj, recursive, depth+1);
			}
		}
		else {
				System.out.printf(" NONE", depth);
			}

	}


	private void inspectArray(Class c, Object obj, boolean recursive, int depth) {
		if (!c.isAssignableFrom(obj.getClass()) || obj == null || !obj.getClass().isArray() || !c.isArray()) {
    		System.out.println("\t Error");
    		return;
    	}
		
		
		Class cType = c.getComponentType();
		System.out.printf("\n" + tabs(depth) + "  Component Type: " + c.getComponentType());
		
		int arrlength = Array.getLength(obj);
		System.out.printf("\n" + tabs(depth) + "  Length: " + Integer.toString(arrlength));
		
		if(arrlength > 0) {
			System.out.printf("\n" + tabs(depth) + "  Entries->");
		}
		
		for(int i = 0; i < arrlength; i++) {
			Object aObj = Array.get(obj, i);
			
			if (aObj == null) {
				System.out.printf("\n" + tabs(depth) + "   Value: null", depth);
		}
			else if(cType.isArray()) {
				System.out.printf("\n" + tabs(depth) + "   Value: " + aObj.toString(), depth);
				inspectArray(aObj.getClass(), aObj, recursive, depth);
			}
			
			else if(cType.isPrimitive()) {
				System.out.printf("\n" + tabs(depth) + "   Value: " + aObj.toString(), depth);
				
			}
			
			else if(!recursive) {
				System.out.printf("\n" + tabs(depth) + "   Value (ref): " + aObj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(aObj)));
			}
			
			else {
				System.out.printf("\n" + tabs(depth) + "   Value (ref): " + aObj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(aObj)));
				System.out.printf("\n" + tabs(depth) + "   -> Recursively inspect\n");
				inspectClass(aObj.getClass(), aObj, recursive, depth+1);
			}
			
		}
		
		
		
	}
    

}
