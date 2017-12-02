package com.ericsuwardi.newsapplication.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class DateTimeHelper {

    public static Date getDate(String dateFormat, String dateStr ){

        DateFormat format = new SimpleDateFormat(dateFormat);

        Date result;
        try{
            result = format.parse(dateStr);
        } catch (ParseException e){
            e.printStackTrace();
            result = new Date();
        }

        return result;
    }

    public static String writeFormattedDate(String format, Date date){

        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try{
            result = dateFormat.format(date);
        }catch (Exception ex ){
            ex.printStackTrace();
        }

        return result;
    }

    public static String getDateTimeDifferenceStr(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        String resulstStr = "";

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;


        if (elapsedDays > 0)
            resulstStr = String.format("%d days ago", elapsedDays);
        else if (elapsedHours > 0)
            resulstStr = String.format("%d hours ago", elapsedHours);
        else if (elapsedMinutes > 0)
            resulstStr = String.format("%d minutes ago", elapsedMinutes);
        else if (elapsedSeconds > 0)
            resulstStr = String.format("%d seconds ago", elapsedSeconds);

        return resulstStr;
    }
}
