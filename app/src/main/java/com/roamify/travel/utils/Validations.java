package com.roamify.travel.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kapil Deo Yadav on 28/08/2017
 * Panalinks Infotech Ltd
 */
public class Validations {
    public String TAG = "Validations";

    public static String[] usernameStringArray(String userID) {
        String str[] = new String[userID.length() - 2];
        for (int i = 0; i < str.length; i++) {
            str[i] = userID.substring(i, i + 3);
        }
        return str;
    }

    public static boolean IsValidPassword(String[] userID, String password) {
        for (int i = 0; i < userID.length; i++) {
            if (password.contains(userID[i])) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> usernameStringArrayList(String userID) {
        ArrayList<String> substrings = new ArrayList<>();
        for (int i = 0; i < userID.length() - 2; i++) {
            substrings.add(userID.substring(i, i + 3));
        }
        //return !substrings.Any(s -> newPassword.contains(s));
        return substrings;
    }

    public static boolean isNotNullNotEmptyNotWhiteSpace(
            final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }

    public static boolean matchString(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        } else {
            return false;
        }
    }

    public static int generateId() {
        int text = 0;
        String possible = "0123456789";

        for (int i = 0; i < possible.length(); i++)
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));

        return text;
    }

    public static String generateUserID() {
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 11; i++)
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));

        return text;
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        //final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidUrl(String txtWebsite) {
        if (!txtWebsite.equals("")) {
            Pattern regex = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)$");
            Matcher matcher = regex.matcher(txtWebsite);
            if (matcher.matches()) {
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public static String removeExtention(String filePath) {
        // These first few lines the same as Justin's
        File f = new File(filePath);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) return filePath;

        String name = f.getName();

        // Now we know it's a file - don't need to do any special hidden
        // checking or contains() checking because of:
        final int lastPeriodPos = name.lastIndexOf('.');
        if (lastPeriodPos <= 0) {
            // No period after first character - return name as it was passed in
            return filePath;
        } else {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            return renamed.getPath();
        }
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        System.out.println(phoneNumber.length());
        String regex = "^\\+?[0-9. ()-]{0,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            System.out.println("Phone Number Valid");
            return true;
        } else {
            System.out.println("Phone Number must be in the form XXX-XXXXXXX");
            return false;
        }
    }

    private static String unique_ProfileId() {
        UUID id = UUID.randomUUID();
        return String.valueOf(id);
    }

    public static String unigueId() {
        String imageName = unique_ProfileId() + ".jpg";
        Log.d("IMAGE_NAME", "IMAGE_NAME:" + imageName);
        return imageName;
    }

    public static void setFontTextView(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFontTextView((ViewGroup) v, font);
        }
    }

    public static String replaceSpace(String string) {
        String replacedString = string.replaceAll("", "*");

        return replacedString;
    }

    public static String replaceSlash(String string) {
        String replacedString = string.replaceAll("/", "*");

        return replacedString;

    }

    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public static Age FindAge(Calendar birthDay) {
        int years = 0;
        int months = 0;
        int days = 0;
        int hour = 0;
        int min = 0;
        int sec = 0;

        //create calendar object for current day
        Calendar currentDay = Calendar.getInstance();
        //Get difference between years
        years = currentDay.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = currentDay.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;

            if (currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;

        } else if (months == 0 && currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }


        //Calculate the days
        if (currentDay.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = currentDay.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = currentDay.get(Calendar.DAY_OF_MONTH);
            currentDay.add(Calendar.MONTH, -1);
            days = currentDay.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        hour = currentDay.get(Calendar.HOUR_OF_DAY) - birthDay.get(Calendar.HOUR_OF_DAY);
        if (hour < 0) {
            days--;
            hour += 24;
        }
        min = currentDay.get(Calendar.MINUTE) - birthDay.get(Calendar.MINUTE);
        if (min < 0) {
            hour--;
            if (hour < 0) {
                days--;
                hour += 24;
            }
            min += 60;
        }
        sec = currentDay.get(Calendar.SECOND) - birthDay.get(Calendar.SECOND);
        if (sec < 0) {
            min--;
            if (min < 0) {
                hour--;
                min += 60;
                if (hour < 0) {
                    days--;
                    hour += 24;
                }
            }
            sec += 60;
        }

        System.out.println("The age is : " + years + " years, " + months + " months,  " + days + " days, " + hour + " hours, " + min + " min and " + sec + " seconds.");
        return new Age(days, months, years);
    }//end FindAge method

    public static void rotateProgressImage(ImageView image) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1800);
        image.startAnimation(rotate);
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param activity
     */
    public static void hideSoftInput(final Activity activity) {
        if (activity.getCurrentFocus() != null) {
            new Runnable() {
                public void run() {
                    InputMethodManager imm =
                            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
            }.run();
        }
    }

    /**
     * Hide Soft Keyboard from Dialogs with new Thread
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFrom(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }.run();
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }.run();
    }

    public static void hideKeyboardOnClickOtherView(final Activity context, final View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftInputFrom(context, view);
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                final View innerView = ((ViewGroup) view).getChildAt(i);

                hideKeyboardOnClickOtherView(context, innerView);
            }
        }
    }

    public static boolean isTimeBetweenTwoTime(String argStartTime,
                                               String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            Date startTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            Date currentTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            Date endTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }

    public static final boolean isBetweenValidTime(Date startTime, Date endTime, Date validateTime) {
        boolean validTimeFlag = false;
        if (endTime.compareTo(startTime) <= 0) {
            if (validateTime.compareTo(endTime) < 0 || validateTime.compareTo(startTime) >= 0) {
                validTimeFlag = true;
            }
        } else if (validateTime.compareTo(endTime) < 0 && validateTime.compareTo(startTime) >= 0) {
            validTimeFlag = true;
        }
        return validTimeFlag;
    }

    public static String returnCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                return "Sunday";
            case Calendar.MONDAY:
                // Current day is Monday
                return "Monday";
            case Calendar.TUESDAY:
                // Current day is Tuesday
                return "Tuesday";
            case Calendar.WEDNESDAY:
                // Current day is Wedesday
                return "Wednesday";
            case Calendar.THURSDAY:
                // Current day is Thursday
                return "Thursday";
            case Calendar.FRIDAY:
                // Current day is Friday
                return "Friday";
            case Calendar.SATURDAY:
                // Current day is Saturday
                return "Saturday";
        }
        return "";
    }

    public static boolean doNotNotifyWithinHour(long doNotNotifyWithinHourCurrentTime) {
        boolean withinHour = false;
        try {
            long currentTimeString = System.currentTimeMillis();
            long oldTimeString = doNotNotifyWithinHourCurrentTime;
            try {
                if (oldTimeString > currentTimeString) {
                    //Log.d(TAG, "Do not Notify");
                    withinHour = true;
                    return withinHour;
                } else {
                    //Log.d(TAG, "Do Notify");
                    withinHour = false;
                    return withinHour;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {

        }
        return withinHour;
    }

    public static boolean doNotifyOn(String[] days) {
        //Logic for Do Notify on Day
        String currentDay = returnCurrentDay();
        for (int i = 0; i < days.length; i++) {
            if (currentDay.equals(days[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean notifyBetween(String startTimeIn24HourFormat, String endTimeIn24HourFormat) {
        //Logic for notifyBetween
        SimpleDateFormat msimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        boolean isTimebwTwoTime = false;
        Date currentDate = new Date();
        String currentTimeString = msimpleDateFormat.format(currentDate.getTime());
        try {
            Date cDate = msimpleDateFormat.parse(currentTimeString);
            Date sDate = msimpleDateFormat.parse(startTimeIn24HourFormat);
            Date eDate = msimpleDateFormat.parse(endTimeIn24HourFormat);
            isTimebwTwoTime = isBetweenValidTime(sDate, eDate, cDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTimebwTwoTime;
    }

    public static String formattedTimeStamp(String timstamp, SimpleDateFormat simpleDateFormat) {
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sDate = oldDateFormat.parse(timstamp);
            timstamp = simpleDateFormat.format(sDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return timstamp;
    }

    public static String formattedTime(String timstamp, SimpleDateFormat timeFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date sDate = simpleDateFormat.parse(timstamp);
            timstamp = timeFormat.format(sDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return timstamp;
    }


    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
