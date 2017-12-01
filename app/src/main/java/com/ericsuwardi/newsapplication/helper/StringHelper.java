package com.ericsuwardi.newsapplication.helper;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class StringHelper {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().length() <= 0 );
    }


}
