POJOs2CSV
==============

Simple and lightweight library that convert POJO collections (java.util.Collection) into CSV (Comma Separated Values) strings and files.

This is specially useful when you get a POJO collection like a list of entities from an
[ORM](http://en.wikipedia.org/wiki/Object-relational_mapping) and you want to obtain
a CSV representation of that entities (to generate spreesheet reports for example).

Features
-------
* It generates [RFC4180](http://tools.ietf.org/html/rfc4180) compliant CSV.
* CSV headers are fully customizable.
* You can hide columns (fields of the object type elements in the collection) using annotations.
* Optimized for large collections.

Usage
-----
Download [pojos2csv-1.0.0.jar](https://github.com/miguelcarrasco/collection2csv/releases/download/1.0.0/pojos2csv-1.0.0.jar)
and put it in your project classpath.

> If you want to build the latest jar version by yourself, you need to install [maven](http://maven.apache.org/), 
clone this repository and execute:

> `mvn package` (inside the repository directory)

> This will buid the project generating the jar file in `[REPOSITORY_DIR]/target/collection2csv-x.x.x.jar`

###Transform a POJO collection into a CSV String
To transform a POJO collection (any object that implement a subinterface of java.util.Collection like java.util.List or
java.util.Set) into a CSV String, use the `convertToCsvString()` method:

```java
String csv = POJOs2CSV.convertToCsvString(yourcollection);
```

####Example
Consider the following class:

```java
public class User{
    private String name;
    private String lastName;
    private Long phone;

    public User(String name, String lastName, Long phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    // Getters, Setters and maybe others methods goes here
    ...
}
```
and the userList ArrayList, an implementation of java.util.List (a subinterface of java.util.Collection) of Users:
```java
List<User> usersList = new ArrayList<User>();
usersList.add(new User("John","Doe",(long)555123123));
usersList.add(new User("Andrey","Kolmogorov",(long)888123123));
usersList.add(new User("Évariste","Galois",(long)555121298));
```

Then, if you use the convertToCsvString method like this:
```java
String csv = POJOs2CSV.convertToCsvString(usersList);
```
You will get the following CSV String:
```
"name","lastName","phone"
"John","Doe","555123123"
"Andrey","Kolmogorov","888123123"
"Évariste","Galois","555121298"
```

> Important: The collection must not be empty (it will throw a NoSuchElementException), because there is no way 
to know the type of objects in the collection at runtime (i.e. using reflection) if the collection 
doesn't contains at least one element.

> But If you know the type of objects in the collection, then you can use the method: 

> ```java
> POJOs2CSV.convertToCsvString(yourcollection,YourObjectsType.class);
> ```
> For example:
> ```java
> List<User> userList = new ArrayList<User>();
> POJOs2CSV.convertToCsvString(userList,User.class);
> ```

> And then, even if your POJOs collection is empty, it will generate a CSV String containing the CSV Headers.

###Writing to a file
You can use the appendCsv method to write into a File:

```java
 Writer writer= new FileWriter("example.csv");
 POJOs2CSV.appendCsv(userList, writer);
 writer.close();
```
You can also use the appendCsv method to append the generated CSV into any Object that implements the Appendable interface
(like FileWriter, PrintStream, PrintWriter, BufferedWriter, etc).

###Changing the CSV headers
Sometimes, you want to change the CSV headers to more explicit values, for that purpose
you can use the `@CSVField` annotation.

So, if we add the following annotations to the fields in the User class from the previous example:

```java
public class User{
    @CSVField(name="Name")
    private String name;

    @CSVField(name="Last Name")
    private String lastName;

    private Long phone;

    public User(String name, String lastName, Long phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    // Getters, Setters and maybe others methods goes here
    ...
}
```
Then `POJOs2CSV.convertToCsvString(usersList)` will return this CSV string:

```
"Name","Last Name","phone"
"John","Doe","555123123"
"Andrey","Kolmogorov","888123123"
"Évariste","Galois","555121298"
```

###Hiding class fields (CSV Columns)

Sometimes you want to generate CSV strings without taking account some class fields,
you can do this using the annotation `@CSVField(ignore=true)` to tell `convertToString` method to
"ignore" that field.

So in the previous example if we add the `@CSVField(ignore=true)` annotation to the phone field
in the User class:

```java
public class User{
    @CSVField(name="Name")
    private String name;

    @CSVField(name="Last Name")
    private String lastName;

    @CSVField(ignore=true)
    private Long phone;

    public User(String name, String lastName, Long phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }
    
    // Getters, Setters and maybe others methods goes here
    ...
}
```
Then `POJOs2CSV.convertToCsvString(usersList)` will return this CSV string:
```
"Name","Last Name"
"John","Doe"
"Andrey","Kolmogorov"
"Évariste","Galois"
```

Limitations
-----------
* Currently it doesn't work with inheritance, i.e. if the type of objects in your collection extends from another
class, the generated CSV String will not include the superclass fields (In future versions it will be supported).

Credits
------
* Miguel Ángel Carrasco Wens (<tlacaelel.software@gmail.com>) (original author).

License
-------
Copyright 2013 Miguel Ángel Carrasco Wens (<tlacaelel.software@gmail.com>)

Released under the MIT License.
