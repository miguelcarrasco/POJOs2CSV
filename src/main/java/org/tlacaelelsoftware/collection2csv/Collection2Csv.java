package org.tlacaelelsoftware.collection2csv;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Collection2Csv {

    public static String convertToCsvString(Collection collection) throws IllegalAccessException {

        // Obtaining the base class of the objects in the list.
        Class baseClass = collection.iterator().next().getClass();

        // Obtaining a list of fields using reflection
        List<Field> fieldsList = Arrays.asList(baseClass.getDeclaredFields());

        // Constructing the "csv header" row, using the field names
        String csv = getCsvHeader(fieldsList) + "\n";

        // Fill the content in the csv rows.
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            csv += getCsvRow(obj, fieldsList);
            if (iterator.hasNext()) {
                csv += "\n";
            }
        }

        return csv;
    }

    private static String getFieldContentAsCsv(Field field, Object obj) throws IllegalAccessException {
        Object value = field.get(obj);
        if (value == null) {
            return "";
        }
        return value.toString().replaceAll("\"", "\"\"");
    }

    private static String getCsvRow(Object obj, List<Field> fieldsList) throws IllegalAccessException {
        String csvRow = "";

        Iterator<Field> iterator = fieldsList.iterator();

        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {
                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    // remove the last comma character and continue
                    csvRow = removeLastCharacter(csvRow);
                    continue;
                }
            }

            csvRow += "\"" + getFieldContentAsCsv(field, obj) + "\"";

            if (iterator.hasNext()) {
                csvRow += ",";
            }
        }
        return csvRow;
    }

    private static String getCsvHeader(List<Field> fieldsList) {
        String csvHeader = "";

        Iterator<Field> fieldListIterator = fieldsList.iterator();

        while (fieldListIterator.hasNext()) {
            Field field = fieldListIterator.next();

            CSVField csvFieldAnnotation = field.getAnnotation(CSVField.class);
            if (csvFieldAnnotation != null) {

                // If it's annotated with @CSVField(ignore=true) then...
                if (csvFieldAnnotation.ignore()) {
                    // remove the last comma character and continue
                    csvHeader = removeLastCharacter(csvHeader);
                    continue;
                } else {
                    // set the header field using the @CSVField(name="specified name") specified name
                    csvHeader += "\"" + csvFieldAnnotation.name().replaceAll("\"", "\"\"") + "\"";
                }

            } else {
                csvHeader += "\"" + field.getName() + "\"";
            }

            if (fieldListIterator.hasNext()) {
                csvHeader += ",";
            }
        }

        return csvHeader;
    }

    private static String removeLastCharacter(String str){
        if (str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
