package org.tlacaelelsoftware.pojos2csv;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Contain methods to convert java.util.Collection collections into CSV String representations.
 *
 * @author Miguel Angel Carrasco Wens
 * @version 1.0-SNAPSHOT
 */
public class POJOs2CSV {
    private static final Pattern p = Pattern.compile("\"");

    /**
     * Convert a collection of POJOs into a CSV String representation.
     *
     * @param collection a non-empty Collection.
     * @return a CSV String representation of the collection.
     * @throws IllegalAccessException
     * @throws NoSuchElementException when the collection is empty.
     */
    public static String convertToCsvString(Collection collection) throws IllegalAccessException, NoSuchElementException, IOException {
        StringBuilder csvSb = new StringBuilder();
        appendCsv(collection, csvSb);
        return csvSb.toString();
    }

    /**
     * Convert a collection of elements into a CSV String representation
     *
     * @param collection a Collection
     * @param baseClass  the class of the collection elements, i.e. if the collection passed is a list of
     *                   the type List<SomeClass>, then baseClass is SomeClass.class
     * @return a CSV String representation of the collection.
     * @throws IllegalAccessException
     */
    public static String convertToCsvString(Collection collection, Class baseClass)
            throws IllegalAccessException, IOException {
        StringBuilder csvSb = new StringBuilder();
        appendCsv(collection, csvSb, baseClass);
        return csvSb.toString();
    }

    /**
     * Appends the csv into an Object that implements the interface Appendable
     *
     * @param collection a Collection
     * @param appendable an appendable object (like {@link java.io.FileWriter FileWriter},
     *                   {@link java.lang.StringBuilder StringBuilder}, {@link java.io.PrintStream PrintStream} etc)
     *                   in witch the csv will be appended.
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void appendCsv(Collection collection, Appendable appendable)
            throws IllegalAccessException, IOException {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("The specified collection must not be empty");
        }
        // Obtaining the base class of the objects in the list.
        Class baseClass = collection.iterator().next().getClass();
        appendCsv(collection, appendable, baseClass);
    }

    /**
     * Appends the csv into an Object that implements the interface Appendable
     *
     * @param collection a Collection
     * @param appendable an appendable object (like {@link java.io.FileWriter FileWriter},
     *                   {@link java.lang.StringBuilder StringBuilder}, {@link java.io.PrintStream PrintStream} etc)
     *                   in witch the csv will be appended.
     * @param baseClass  the class of the collection elements, i.e. if the collection passed is a list of
     *                   the type List<SomeClass>, then baseClass is SomeClass.class
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void appendCsv(Collection collection, Appendable appendable, Class baseClass)
            throws IllegalAccessException, IOException {
        // Obtaining a list of fields using reflection
        List<Field> fieldsList = Arrays.asList(baseClass.getDeclaredFields());

        for (Field field : fieldsList) {
            field.setAccessible(true);
        }

        // Constructing the "csv header" row, using the field names
        appendCsvHeader(fieldsList, appendable);

        // Fill the content in the csv rows.
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            appendCsvRow(obj,fieldsList,appendable);
            if (iterator.hasNext()) {
                appendable.append('\n');
            }
        }
    }


    private static String getFieldContentAsCsv(Field field, Object obj) throws IllegalAccessException {
        Object value = field.get(obj);
        if (value == null) {
            return "";
        }

        return replaceCommas(value.toString());
    }

    private static String replaceCommas(String string) {
        if (string.contains("\"")) {
            return p.matcher(string).replaceAll("\"\"");
        } else {
            return string;
        }
    }

    private static void appendCsvRow(Object obj, List<Field> fieldsList,Appendable appendable) throws IllegalAccessException, IOException {

        String prefix = "";
        for (Field field : fieldsList) {

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {
                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    continue;
                }
            }
            appendable.append(prefix).append("\"").append(getFieldContentAsCsv(field, obj)).append("\"");

            prefix = ",";
        }

    }

    private static void appendCsvHeader(List<Field> fieldsList,Appendable appendable) throws IOException {

        String prefix = "";
        for (Field field : fieldsList) {

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {

                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    continue;
                } else {
                    // set the header field using the @CSVField(name="specified name") specified name
                    appendable.append(prefix).append("\"").append(replaceCommas(csvFieldAnnotation.name())).append("\"");
                }

            } else {
                appendable.append(prefix).append("\"").append(field.getName()).append("\"");
            }

            prefix = ",";
        }
        appendable.append("\n");

    }

}
