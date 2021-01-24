# CPSC-501

Repository of assignments completed in CPSC 501. 

## A2 - Reflective Object Inspector

**Goal** - Experience and develop understanding of reflection.

**Technology Used** - Java, Git, Gitlab, Reflection

**Description** - The goal of this assignment is to create a reflective object inspector that dos a complete intospection of and object at runtime. The inspector is implemented in a Java class called Inspector, and will be invoked using the method 

**public void inspect(Object obj, boolean recursive)**

This method will introspect on the object specified by the first parameter obj, printing what it finds to standard output. 
The following information is displayed for the object:
1. The **name** of the declaring **class**
2. The **name** of the immediate **super-class**\
  a. Always explore super-class immediately and recursively (even if recursive=false)\
3. The **name** of each **interface** the class implements\
  a. Always explore interfaces immediately and recursively (even if recursive=false)\
4. The **constructors** the class declares. And the following:\
  a. The **name**\
  b. The **exceptions** thrown\
  c. The **parameter types**\
  d. The **modifiers**
5. The methods the class declares. And the following:\
  a. The **name**\
  b. The **exceptions** thrown\
  c. The **parameter types**\
  d. The **return-type**\
  e. The **modifiers**
6. The **fields** the class declares. And the following:\
  a. The **name**\
  b. The **type**\
  c. The **modifiers**\
  d. The **current value**\
      i. If the field is an object reference, and recursive is set to false, then simply print out the “reference value directly (this will be the name of the object’s class plus the object’s “identity hash code” ex. **java.lang.Object@7d4991ad)**.\
      ii. If the field is an object reference, and recursive is set to true, then immediately recurse on the object.
      
## A3 - Reflective Object Serialization

**Goal** - Using reflection to serialize an object to JSON and then de-serialize from JSON back to an object

**Technology Used** - Java, JSON, Git, Gitlab, Reflection

**Description** - The goal of this assignment is to create two programs (that could be run on two separate computers) that communicate with each other over a network connection. The **Sender** program will create an **object** (that may have other internally referenced objects). **Sender** will **serialize** each object created into a **JSON** String, and then send this **JSON** String as a stream of bytes to the **Receiver** program using a socket connection. The **Receiver** program will convert the incoming byte stream into a **JSON** String, **deserialize** the **JSON** into an **object**, and **display** the **object** to screen.
      
