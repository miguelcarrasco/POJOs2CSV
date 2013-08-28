package org.tlacaelelsoftware.collection2csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {
    String name() default "undefined";
    boolean ignore() default false;
}
