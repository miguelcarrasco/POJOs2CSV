package org.tlacaelelsoftware.pojos2csv;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Contain methods to convert java.util.Collection collections into CSV String representations.
 *
 * @author Miguel Angel Carrasco Wens
 * @version 1.0-SNAPSHOT
 */
public class POJOs2CSV {

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
     * @param collection    a Collection
     * @param csvAppendable an appendable object (like {@link java.io.FileWriter FileWriter},
     *                      {@link java.lang.StringBuilder StringBuilder}, {@link java.io.PrintStream PrintStream} etc)
     *                      in witch the csv will be appended.
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void appendCsv(Collection collection, Appendable csvAppendable)
            throws IllegalAccessException, IOException {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("The specified collection must not be empty");
        }
        // Obtaining the base class of the objects in the list.
        Class baseClass = collection.iterator().next().getClass();
        appendCsv(collection, csvAppendable, baseClass);
    }

    /**
     * Appends the csv into an Object that implements the interface Appendable
     *
     * @param collection    a Collection
     * @param csvAppendable an appendable object (like {@link java.io.FileWriter FileWriter},
     *                      {@link java.lang.StringBuilder StringBuilder}, {@link java.io.PrintStream PrintStream} etc)
     *                      in witch the csv will be appended.
     * @param baseClass     the class of the collection elements, i.e. if the collection passed is a list of
     *                      the type List<SomeClass>, then baseClass is SomeClass.class
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void appendCsv(Collection collection, Appendable csvAppendable, Class baseClass)
            throws IllegalAccessException, IOException {
        // Obtaining a list of fields using reflection
        List<Field> fieldsList = Arrays.asList(baseClass.getDeclaredFields());

        // Constructing the "csv header" row, using the field names
        csvAppendable.append(getCsvHeader(fieldsList)).append('\n');

        // Fill the content in the csv rows.
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            csvAppendable.append(getCsvRow(obj, fieldsList));
            if (iterator.hasNext()) {
                csvAppendable.append('\n');
            }
        }
    }


    private static String getFieldContentAsCsv(Field field, Object obj) throws IllegalAccessException {
        Object value = field.get(obj);
        if (value == null) {
            return "";
        }
        return value.toString().replaceAll("\"", "\"\"");
    }

    private static String getCsvRow(Object obj, List<Field> fieldsList) throws IllegalAccessException {
        Iterator<Field> iterator = fieldsList.iterator();

        List<String> fieldsInRow = new ArrayList<String>();

        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {
                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    continue;
                }
            }
            fieldsInRow.add("\"" + getFieldContentAsCsv(field, obj) + "\"");
        }
        return join(fieldsInRow, ",");
    }

    private static String getCsvHeader(List<Field> fieldsList) {

        Iterator<Field> fieldListIterator = fieldsList.iterator();
        List<String> headers = new ArrayList<String>();

        while (fieldListIterator.hasNext()) {
            Field field = fieldListIterator.next();

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {

                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    continue;
                } else {
                    // set the header field using the @CSVField(name="specified name") specified name
                    headers.add("\"" + csvFieldAnnotation.name().replaceAll("\"", "\"\"") + "\"");
                }

            } else {
                headers.add("\"" + field.getName() + "\"");
            }
        }

        return join(headers, ",");
    }

    private static String join(List<String> stringList, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String value : stringList) {
            sb.append(value);
            sb.append(separator);
        }
        int length = sb.length();
        if (length > 0) {
            // Remove the extra delimiter
            sb.setLength(length - separator.length());
        }
        return sb.toString();
    }

}
