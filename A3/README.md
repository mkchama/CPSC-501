To run the program you can open the project on VS Code. When the project is loaded, you will first run the **Client.java** file followed by the **Server.java** file. When these two are up and running, the user will be prompted on the Server terminal interface to choose from which objects you would like to create. A demo video will be uploaded and linked. 

**Refactorings**

The first refactor was removing the bad smell code of repeating System.out.print calls, and replacing it with a method to do those calls. This refacoring was performed in the **ObjectCreator.java** file.

This was the code before.

```
System.out.println("Welcome: choose from the following options");
System.out.println("  1: Create ObjectA: ");
System.out.println("  2: Create ObjectB: ");
System.out.println("  3: Create ObjectC: ");
System.out.println("  4: Create ObjectD: ");
System.out.println("  5: Create ObjectE: ");
System.out.println("  6: Exit");
```



The code after was inserting these prints into a method.

```
public static void printMenu() {
        System.out.println("Welcome: choose from the following options");
        System.out.println("  1: Create ObjectA: ");
        System.out.println("  2: Create ObjectB: ");
        System.out.println("  3: Create ObjectC: ");
        System.out.println("  4: Create ObjectD: ");
        System.out.println("  5: Create ObjectE: ");
        System.out.println("  6: Exit");
    }
```

