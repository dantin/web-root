package com.demo2do.core.web.interceptor;

import java.lang.annotation.*;

/**
 * Annotation for Info Message
 *
 * @author David
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface InfoMessage {

    String value() default "";

    String model() default "";

}
