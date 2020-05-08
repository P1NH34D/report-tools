package com.alchemistscode.commons.tools.label;

import com.alchemistscode.commons.tools.enums.AlternateText;
import com.alchemistscode.commons.tools.enums.Formatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author  pinhead - arturo.tovar@kode.com.mx
 * @version 1.0.0
 * @since  Ago. 02, 2019.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnTitle {
    String value() default "";
    AlternateText alternativeText() default AlternateText.DEFAULT;
    HorizontalAlignment alignment() default HorizontalAlignment.LEFT;
    Formatter format() default Formatter.DECIMAL;
}
