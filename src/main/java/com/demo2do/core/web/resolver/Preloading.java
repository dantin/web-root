package com.demo2do.core.web.resolver;

import java.lang.annotation.*;

/**
 * Annotation for Preloading entity
 *
 * @author David
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Preloading {

    String value() default "";

}
