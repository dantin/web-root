package com.demo2do.core.web.resolver;

import java.lang.annotation.*;

/**
 * Secure annotation
 *
 * @author David
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secure {

    String property() default "";

}
