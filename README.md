Collection2Csv
==============

Simple utility to convert generic collections (java.util.Collection) into CSV strings.

This is special useful when you get a collection like a list from an ORM and you want
a CSV string representation of the elements of the list (to generate reports for example).

(This work is in beta stage, not ready for production yet)

Usage
-----

As an example, consider the following class:

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
and this java.util.List (a subinterface of java.util.Collection) of Users:
```java
List<User> usersList = new ArrayList<User>();
usersList.add(new User("John","Doe",(long)555123123));
usersList.add(new User("Andrey","Kolmogorov",(long)888123123));
usersList.add(new User("Évariste","Galois",(long)555121298));
```

Then, if you use the convertToCsvString method:
```java
String csv = Collection2Csv.convertToCsvString(usersList);
```
The csv string will be:
```
"name","lastName","phone"
"John","Doe","555123123"
"Andrey","Kolmogorov","888123123"
"Évariste","Galois","555121298"
```

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

###Hiding class fields (CSV Columns):

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

Author
------
* Miguel Ángel Carrasco Wens (<tlacaelel.software@gmail.com>)
