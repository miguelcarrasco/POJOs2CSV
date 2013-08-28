package org.tlacaelelsoftware.collection2csv;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        list.add(new SimpleAnnotatedPOJOForTest("testuser", "test@test.org", (long) 1));
        list.add(new SimpleAnnotatedPOJOForTest("test, \"user\"", "another@test.org", (long) 1));

        String expectedCsv =
                "\"User\",\"Email\"\n" +
                        "\"testuser\",\"test@test.org\"\n" +
                        "\"test, \"\"user\"\"\",\"another@test.org\"";

        Assert.assertEquals("The expected csv must be equal to the convertToCsvString() output",
                expectedCsv, Collection2Csv.convertToCsvString(list));
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

    SimpleAnnotatedPOJOForTest(String user, String email, Long userId) {
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