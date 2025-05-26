package com.example.tinu;

import java.text.DecimalFormat;
import java.util.Random;

public class OperationHandler {
    public static String executeConversion(UnitOptions.grandeza grandeza, Enum<?> unit1, Enum<?> unit2, String value) {
        if (value.isEmpty()) return "";

        double parsed = Double.parseDouble(value);

        double result = UnitConverter.converter(parsed, grandeza, unit1.ordinal(), unit2.ordinal());

        DecimalFormat format = new DecimalFormat("0.##########");

        return format.format(result);
    }
}
