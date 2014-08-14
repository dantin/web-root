package com.demo2do.core.utils;

import java.lang.annotation.*;

/**
 * Alias annotation for Enumeration
 *
 * @author David
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {

    String value() default "";

}
