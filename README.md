POJOs2CSV
==============

Simple and lightweight library that convert POJO collections (like List, Set, Map, etc) into CSV (Comma Separated Values) strings and files.

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
Download [pojos2csv-1.0.0.jar](https://github.com/miguelcarrasco/collection2csv/releases/download/0.1.0-SNAPSHOT/pojos2csv-1.0.0.jar)
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
public class User {
    private Long id;
    private String name;
    private String lastName;

    public User(Long id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    // Getters, Setters and maybe others methods goes here
    ...
}
```
and the userList ArrayList, an implementation of java.util.List (a subinterface of java.util.Collection) of Users:
```java
List<User> users = new ArrayList<User>();
users.add(new User(1L, "Andrey", "Kolmogorov"));
users.add(new User(2L, "Évariste", "Galois"));
users.add(new User(3L, "David", "Hilbert"));
```

Then, if you use the convertToCsvString method like this:
```java
String csv = POJOs2CSV.convertToCsvString(usersList);
```
You will get the following CSV String:
```
"id","name","lastName"
"1","Andrey","Kolmogorov"
"2","Évariste","Galois"
"3","David","Hilbert"
```

> Important: The collection must not be empty (it will throw a NoSuchElementException), because there is no way 
to know the type of objects in the collection at runtime (i.e. using reflection) if the collection 
doesn't contains at least one element.

> But If you know the type of objects in the collection, then you can use the method: 

> ```java
> POJOs2CSV.convertToCsvString(yourcollection, YourObjectsType.class);
> ```
> For example:
> ```java
> List<User> users = new ArrayList<User>();
> String csv = POJOs2CSV.convertToCsvString(users, User.class);
> ```

> And then, even if your POJOs collection is empty, it will generate a CSV String containing the CSV Headers.

###Writing to a file
You can use the appendCsv method to write into a File:

```java
 Writer writer = new FileWriter("example.csv");
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
public class User {
    private Long id;

    @CSVField(name = "Name")
    private String name;

    @CSVField(name = "Last Name")
    private String lastName;

    public User(Long id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    // Getters, Setters and maybe others methods goes here
    ...
}
```
Then `POJOs2CSV.convertToCsvString(users)` will return this CSV string:

```
"id","Name","Last Name"
"1","Andrey","Kolmogorov"
"2","Évariste","Galois"
"3","David","Hilbert"
```

###Hiding class fields (CSV Columns)

Sometimes you want to generate CSV strings without taking account some class fields,
you can do this using the annotation `@CSVField(ignore = true)` to tell `convertToString` method to
"ignore" that field.

So in the previous example if we add the `@CSVField(ignore = true)` annotation to the id field
in the User class:

```java
public class User {
    @CSVField(ignore = true)
    private Long id;

    @CSVField(name = "Name")
    private String name;

    @CSVField(name = "Last Name")
    private String lastName;

    public User(Long id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    // Getters, Setters and maybe others methods goes here
    ...
}
```
Then `POJOs2CSV.convertToCsvString(users)` will return this CSV string:
```
"Name","Last Name"
"Andrey","Kolmogorov"
"Évariste","Galois"
"David","Hilbert"
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
