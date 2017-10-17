package com.example.guojing.my_minresume.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AmazingLu on 8/24/17.
 */

/**
 * user class use to change date from date to string and string to date
 * the string format is MM/yyyy
 * */

public class DateUtil {
    // first input is the format of the string date
    // second is the locale in a sepcific area of the world
    static private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
    // change the date to string
    public  static  String dateToString (Date date) {
        return sdf.format(date);
    }
    // change the date format to string date
    public static Date stringToDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}
