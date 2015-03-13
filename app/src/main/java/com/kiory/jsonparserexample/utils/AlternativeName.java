package com.kiory.jsonparserexample.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by darknoe on 13/03/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AlternativeName {

    /**
     * @return the desired name of the field when it is serialized
     */
    String value();
}