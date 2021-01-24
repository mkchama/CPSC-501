**Assignment 2 - Reflective Object Inspector**

The purpose of this assignment was to get an understanding of how to use reflection inside of Java. This codebase contains an inspector file that was used to inspect the classes, **ClassA**, **ClassB**, **ClassC**,  **ClassD**, and also contains a **Driver** file to run the program, which will output script files numbered 1-8.

**Refactorings**

The refactorings performed in the **Inspector** file was creating a function: 

    private String tabs(int depth){

		String tab = new String(new char[depth]).replace('\0', '\t');

		return tab;

	} 


This function takes an int value, **depth**, and processes the amount of tabs needed for each recursion performed in the inspection. This was made to properly format the output files, so that the reflection was clear.

The next refactoring performed was removing redundant code that appeared multiple times in each method


    if (!c.isAssignableFrom(obj.getClass()) || obj == null) {

    	System.out.println("\t Error");

    		return;

    	}


and creating a new method to store this if statement, along with the print.

    private void errorCheck(Object obj, Class c){

		if (!c.isAssignableFrom(obj.getClass()) || obj == null) {

    		System.out.println("\t Error");

    		return;

    	}

	}

    
These refactorings helped improve the bad code smell which was repeated code. After the refactorings were completed, the code still ran properly without any issues.

    

