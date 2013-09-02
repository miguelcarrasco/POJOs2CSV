package org.tlacaelelsoftware.collection2csv;

import org.junit.Assert;
import org.junit.Test;
import org.tlacaelelsoftware.collection2csv.testhelpers.SimpleAnnotatedPOJOForTest;
import org.tlacaelelsoftware.collection2csv.testhelpers.SimpleAnnotatedPOJOForTest2;
import org.tlacaelelsoftware.collection2csv.testhelpers.SimplePOJOForTest;
import org.tlacaelelsoftware.collection2csv.testhelpers.SimplePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Collection2CsvTest {
    @Test
    public void testConvertToCsvString() throws Exception {

        // Constructing a list with two elements.
        List<SimplePojo> list = getListForTest(SimplePOJOForTest.class);

        String expectedCsv =
                "\"user\",\"email\",\"userId\",\"phone\"\n" +
                        "\"testuser\",\"test@test.org\",\"1\",\"12345\"\n" +
                        "\"test, \"\"user\"\"\",\"another@test.org\",\"2\",\"12345\"";

        Assert.assertEquals("The expected csv must be equal to the convertToCsvString() output",
                expectedCsv, Collection2Csv.convertToCsvString(list));

    }

    @Test
    public void testConvertToCsvStringWithAnnotations() throws Exception {
        // Constructing a list with two annotated elements.
        List<SimplePojo> list = getListForTest(SimpleAnnotatedPOJOForTest.class);

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
        List<SimplePojo> list = getListForTest(SimpleAnnotatedPOJOForTest2.class);

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

    private List<SimplePojo> getListForTest(Class simplePojoClass)
            throws IllegalAccessException, InstantiationException {

        List<SimplePojo> simplePojoList = new ArrayList<SimplePojo>();

        SimplePojo row1 = (SimplePojo) simplePojoClass.newInstance();
        row1.fakeConstructor("testuser", "test@test.org", (long) 1, "12345");

        SimplePojo row2 = (SimplePojo) simplePojoClass.newInstance();
        row2.fakeConstructor("test, \"user\"", "another@test.org", (long) 2, "12345");

        simplePojoList.add(row1);
        simplePojoList.add(row2);

        return simplePojoList;
    }
}
