package com.alchemistscode.commons.tools.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  pinhead - arturo.tovar@kode.com.mx
 * @version 1.0.0
 * @since  Ago. 02, 2019.
 */
public enum AlternateText {
    DEFAULT("-"),
    NOT_APPLY("N/A"),
    NOT_AVAILABLE("N/D"),
    EMPTY("");

    @Getter
    private String text;

    AlternateText(String text) {
        this.text = text;
    }

    public static List<String> valuesText(){
        List<String> values = new ArrayList<>();
        for (AlternateText value : AlternateText.values()) {
            values.add(value.getText());
        }
        return values;
    }
}
