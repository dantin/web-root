package com.demo2do.core.web.format;

import java.lang.annotation.*;

/**
 * Persistent Format Annotation
 *
 * @author David
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PersistentFormat {
}
