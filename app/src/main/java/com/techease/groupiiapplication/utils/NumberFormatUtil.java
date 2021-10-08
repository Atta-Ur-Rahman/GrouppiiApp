package com.techease.groupiiapplication.utils;

import java.text.DecimalFormat;

public class NumberFormatUtil {

    public static String FormatPercentage(Double percentage) {
        return new DecimalFormat("#.##").format(percentage)+"%";
    }

    public static String FormatPercentageShowCircle(Double percentage) {
        return new DecimalFormat("#.##").format(percentage);
    }
}
