package com.jihazardrestapi.demorestapi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(ElementType.METHOD)
@Retention(SOURCE)
public @interface TestDescription {

    String value();
}
