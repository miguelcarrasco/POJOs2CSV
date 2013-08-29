package org.tlacaelelsoftware.collection2csv;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Collection2CsvTest {
    @Test
    public void testConvertToCsvString() throws Exception {

        // Constructing a list with two elements.
        List<SimplePOJOForTest> list = new ArrayList<SimplePOJOForTest>();
        list.add(new SimplePOJOForTest("testuser", "test@test.org", (long) 1));
        list.add(new SimplePOJOForTest("test, \"user\"", "another@test.org", (long) 2));

        String expectedCsv =
                "\"user\",\"email\",\"userId\"\n" +
                        "\"testuser\",\"test@test.org\",\"1\"\n" +
                        "\"test, \"\"user\"\"\",\"another@test.org\",\"2\"";

        Assert.assertEquals("The expected csv must be equal to the convertToCsvString() output",
                expectedCsv, Collection2Csv.convertToCsvString(list));

    }

    @Test
    public void testConvertToCsvStringWithAnnotations() throws Exception {
        // Constructing a list with two annotated elements.
        List<SimpleAnnotatedPOJOForTest> list = new ArrayList<SimpleAnnotatedPOJOForTest>();
        list.add(new SimpleAnnotatedPOJOForTest("testuser", "test@test.org", (long) 1,"12345"));
        list.add(new SimpleAnnotatedPOJOForTest("test, \"user\"", "another@test.org", (long) 2,"12345"));

        String expectedCsv =
                "\"User\",\"Email\",\"Phone\"\n" +
                        "\"testuser\",\"test@test.org\",\"12345\"\n" +
                        "\"test, \"\"user\"\"\",\"another@test.org\",\"12345\"";

        Assert.assertEquals("The expected csv must be equal to the convertToCsvString() output",
                expectedCsv, Collection2Csv.convertToCsvString(list));
    }

    @Test
    public void testConvertToCsvStringWithDifferentAnnotations() throws Exception {
        // Constructing a list with two annotated elements.
        List<SimpleAnnotatedPOJOForTest2> list = new ArrayList<SimpleAnnotatedPOJOForTest2>();
        list.add(new SimpleAnnotatedPOJOForTest2("testuser", "test@test.org", (long) 1,"12345"));
        list.add(new SimpleAnnotatedPOJOForTest2("test, \"user\"", "another@test.org", (long) 2,"12345"));

        String expectedCsv =
                "\"User\",\"Email\",\"UserId\"\n" +
                        "\"testuser\",\"test@test.org\",\"1\"\n" +
                        "\"test, \"\"user\"\"\",\"another@test.org\",\"2\"";

        Assert.assertEquals("The expected csv must be equal to the convertToCsvString() output",
                expectedCsv, Collection2Csv.convertToCsvString(list));
    }

    @Test
    public void testConvertToCsvStringEmptyCollections() throws Exception {
        List<SimplePOJOForTest> list = new ArrayList<SimplePOJOForTest>();

        String expectedCsv = "\"user\",\"email\",\"userId\"";

        try {
            Collection2Csv.convertToCsvString(list);
            Assert.fail("A NoSuchElementException must be throwed");
        } catch (NoSuchElementException e) {
            // Assert passed
        }
    }
}

//----------------------------------- Helper Classes ---------------------------------------------

/**
 * A Simple Plain Old Java Object (POJO) to test
 * csv conversion
 */
class SimplePOJOForTest {
    private String user;
    private String email;
    private Long userId;

    SimplePOJOForTest(String user, String email, Long userId) {
        this.user = user;
        this.email = email;
        this.userId = userId;
    }

    String getUser() {
        return user;
    }

    void setUser(String user) {
        this.user = user;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    Long getUserId() {
        return userId;
    }

    void setUserId(Long userId) {
        this.userId = userId;
    }
}


/**
 * A Simple Plain Old Java Object (POJO) with annotation
 * to test csv conversion.
 */
class SimpleAnnotatedPOJOForTest {
    @CSVField(name = "User")
    private String user;

    @CSVField(name = "Email")
    private String email;

    @CSVField(ignore = true)
    private Long userId;

    @CSVField(name="Phone")
    private String phone;

    SimpleAnnotatedPOJOForTest(String user, String email, Long userId, String phone) {
        this.user = user;
        this.email = email;
        this.userId = userId;
        this.phone = phone;
    }

    String getUser() {
        return user;
    }

    void setUser(String user) {
        this.user = user;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    Long getUserId() {
        return userId;
    }

    void setUserId(Long userId) {
        this.userId = userId;
    }
}
/**
 * A Simple Plain Old Java Object (POJO) with annotation
 * to test csv conversion.
 */
class SimpleAnnotatedPOJOForTest2 {
    @CSVField(name = "User")
    private String user;

    @CSVField(name = "Email")
    private String email;

    @CSVField(name="UserId")
    private Long userId;

    @CSVField(ignore=true)
    private String phone;

    SimpleAnnotatedPOJOForTest2(String user, String email, Long userId, String phone) {
        this.user = user;
        this.email = email;
        this.userId = userId;
        this.phone = phone;
    }

    String getUser() {
        return user;
    }

    void setUser(String user) {
        this.user = user;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    Long getUserId() {
        return userId;
    }

    void setUserId(Long userId) {
        this.userId = userId;
    }
}