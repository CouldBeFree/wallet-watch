package com.expenses.walletwatch.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatParser {
    public static Date ConvertDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsed = format.parse(date);
            return new Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
