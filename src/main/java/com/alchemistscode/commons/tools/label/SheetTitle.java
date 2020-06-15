package com.alchemistscode.commons.tools.label;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author  pinhead - arturo.tovar@alchemistscode.com
 * @version 1.0 Ago. 02, 2019.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SheetTitle {
    String value();
}
