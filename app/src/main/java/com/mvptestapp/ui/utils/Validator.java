package com.mvptestapp.ui.utils;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android-1 on 30-11-2015.
 */
public class Validator {
    static Pattern pattern;
    static Matcher matcher;
    Calendar mCalendar;
    public static final int MAXIMUM_VALID_YEAR_DIFFERENCE = 20;

    /**
     * Method to validate UserDetailResponse's name which contains only alphabetic values
     *
     * @param email : input Text
     * @return boolean
     */


    public static boolean pancardValidatore(String email) {
        final String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        pattern = Pattern.compile(PAN_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean voterIdValidatore(String email) {
        final String VPTER_PATTERN = "[A-Z]{3}[0-9]{7}";
        pattern = Pattern.compile(VPTER_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //  String rx = "[A-Z]{3}([CHFATBLJGP])(?:(?<=P)" + c1 + "|(?<!P)" + c2 + ")[0-9]{4}[A-Z]";


    public final static boolean isValidEmail(CharSequence target) {
        // if email address is empty retuen false
        if (TextUtils.isEmpty(target))
            return false;
            //compare target and return true if email address is valid else false
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
