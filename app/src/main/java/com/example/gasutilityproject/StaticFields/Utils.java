package com.example.gasutilityproject.StaticFields;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Utils {
    public static boolean containsNonDigits(String input) {
        String regex = ".*\\D.*";
        return Pattern.matches(regex, input);
    }

    public static boolean containsNumbers(String input) {
        String regex = ".*\\d.*";
        return Pattern.matches(regex, input);
    }

    public static String convertToEnglishNumbers(String str) {
        String[] digits = {"۰","۱","۲","۳","۴","۵","۶","۷","۸","۹"};
        for (int i = 0; i < 10; i++) {
            str = str.replaceAll(digits[i].trim(), String.valueOf(i));
        }
        return str;
    }
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
