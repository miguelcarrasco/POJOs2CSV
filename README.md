Collection2Csv
==============

Simple utility to convert generic collections (java.util.Collection) into CSV strings.

This is specially useful when you get a collection like a list from an 
[ORM](http://en.wikipedia.org/wiki/Object-relational_mapping) and you want to obtain
a CSV string representation of the elements in the list (to generate spreesheet reports for example).

Features
-------
* It generates [RFC4180](http://tools.ietf.org/html/rfc4180) compliant CSV Strings.
* CSV headers are fully customizables.
* You can hide columns (fields in collection classes) using annotations.

Usage
-----
Download [collection2csv-0.1.0-SNAPSHOT.jar](https://github.com/miguelcarrasco/collection2csv/releases/download/0.1.0-SNAPSHOT/collection2csv-0.1.0-SNAPSHOT.jar)
and put it in your project classpath.

> If you want to build the latest jar version by yourself, you need to install [maven](http://maven.apache.org/), 
clone this repository and execute:

> `mvn package` (inside the repository directory)

> This will buid the project generating the jar file in `[REPOSITORY_DIR]/target/collection2csv-x.x.x-SNAPSHOT.jar`

###Transform a collection into a CSV String
To transform a collection (any object that implement a subinterface of java.util.Collection like java.util.List or 
java.util.Set) into a CSV String, use the `convertToCsvString()` method:

```java
String csv = Collection2Csv.convertToCsvString(yourcollection);
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
String csv = Collection2Csv.convertToCsvString(usersList);
```
You will get the following CSV String:
```
"name","lastName","phone"
"John","Doe","555123123"
"Andrey","Kolmogorov","888123123"
"Évariste","Galois","555121298"
```

> Important: The collection must not be empty (it will throw a NoSuchElementException), because there is no way 
to know the type of objects in the collection at runtime if the collection doesn't have at least one element.

> But If you know the type of objects in the collection, then you can use the method: 

> ```java
> Collection2Csv.convertToCsvString(yourcollection,YourObjectsType.class);
> ```
> For example:
> ```java
> List<User> userList = new ArrayList<User>();
> Collection2Csv.convertToCsvString(userList,User.class);
> ```

> And then, even if you collection is empty, it will generate a CSV String containing only the CSV Headers.

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
Then `Collection2Csv.convertToCsvString(usersList)` will return this CSV string:

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
Then `Collection2Csv.convertToCsvString(usersList)` will return this CSV string:
```
"Name","Last Name"
"John","Doe"
"Andrey","Kolmogorov"
"Évariste","Galois"
```

Limitations
-----------
* Currently it doesn't work with inheritance, i.e. if the type of objects in your collections extends from another
class, the generated CSV String will not include the superclass fields (In future versions this will be supported).

Author
------
* Miguel Ángel Carrasco Wens (<tlacaelel.software@gmail.com>)
