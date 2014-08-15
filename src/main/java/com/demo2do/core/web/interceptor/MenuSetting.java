package com.demo2do.core.web.interceptor;

import java.lang.annotation.*;

/**
 * Annotation for MenuSetting
 *
 * @author David
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MenuSetting {

    String value() default "";

}
