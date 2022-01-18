package com.techease.groupiiapplication.utils;

import java.text.DecimalFormat;

public class NumberFormatUtil {

    public static String FormatPercentage(Double percentage) {
        return new DecimalFormat("#.##").format(percentage) + "%";
    }

    public static String FormatPercentageShowCircle(Double percentage) {
        return new DecimalFormat("#.##").format(percentage);
    }

    public static String PaymentFormat(Double d) {
        DecimalFormat formatter = new DecimalFormat("#,#00.00#");
        String yourFormattedString = formatter.format(d);

        return yourFormattedString;
    }
}
