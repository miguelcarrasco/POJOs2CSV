package org.tlacaelelsoftware.list2csv;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class List2Csv {

    public static String convertToCsvString(List list) throws IllegalAccessException {

        // Obtaining the base class of the objects in the list.
        Class baseClass = list.iterator().next().getClass();

        // Obtaining a list of fields using reflection
        List<Field> fieldsList = Arrays.asList(baseClass.getDeclaredFields());

        // Constructing the "csv header" row, using the field names
        String csv = getCsvHeader(fieldsList) + "\n";

        // Fill the content in the csv rows.
        Iterator iterator = list.iterator();
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
        if(value == null){
            return "";
        }
        return value.toString().replaceAll("\"","\"\"");
    }

    private static String getCsvRow(Object obj, List<Field> fieldsList) throws IllegalAccessException {
        String csvRow = "";

        Iterator<Field> iterator = fieldsList.iterator();

        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);
            csvRow += "\"" + getFieldContentAsCsv(field,obj) + "\"";
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
            csvHeader += "\"" + field.getName() + "\"";
            if (fieldListIterator.hasNext()) {
                csvHeader += ",";
            }
        }

        return csvHeader;
    }
}
