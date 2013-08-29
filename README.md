Collection2Csv
==============

Simple utility to convert generic collections (java.util.Collection) into CSV strings.

This is special useful when you get a collection like a list from an ORM and you want
a CSV string representation of the elements of the list (to generate reports for example).

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
String csv = Collection2Csv.convertToCsvString(usersList))
```
The csv string will be:
```
"name","lastName","phone"
"John","Doe","555123123"
"Andrey","Kolmogorov","888123123"
"Évariste","Galois","555121298"
```



Author
------
Miguel Ángel Carrasco Wens (<tlacaelel.software@gmail.com>)
