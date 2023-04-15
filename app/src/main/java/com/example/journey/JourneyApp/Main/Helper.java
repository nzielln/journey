package com.example.journey.JourneyApp.Main;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import android.widget.Toast;

import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

public class Helper {
    // KEYS
    public static final String USER_MODEL = "USER_MODEL";

    public static String MOCK_USER_ID = UUID.randomUUID().toString();
    public static Integer RANK_NUMBER = 0;
    public static String todoISOToDate(String isoString) {
        DateTimeFormatter isoToDateFormat = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime date = LocalDateTime.parse(isoString, isoToDateFormat);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return dateFormat.format(date);
    }

    public static ArrayList<TaskItemModel> getCompletedTasks(ArrayList<TaskItemModel> items) {
        return items.stream().filter(TaskItemModel::getCompleted).collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<TaskItemModel> getToBeCompletedTasks(ArrayList<TaskItemModel> items) {
        return items.stream().filter(task -> !task.getCompleted()).collect(Collectors.toCollection(ArrayList::new));
    }

    static public String getLongDateTime() {
        Date date = new Date();
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(date);
    }

    static public String getShortDate() {
        Date date = new Date();
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    static public String getLongDate() {
        Date date = new Date();
        return DateFormat.getDateInstance(DateFormat.LONG).format(date);
    }
    static public String getShortTime() {
        Date date = new Date();
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }

    static public String getDateFrom(Date date) {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date);
    }

    static public String getTimeFrom(int hour, int minute) {
        String time = "AM";
        if (hour > 12) {
            hour -= 12;
            time = "PM";
        } else if (hour == 0) {
            hour += 12;

        } else if (hour == 12) {
            time = "PM";
        }

        String hourString;
        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = "" + hour;
        }

        String timeString;
        if (minute < 10) {
            timeString = "0" + minute;
        } else {
            timeString = "" + minute;
        }

        return hourString + ":" + timeString + " " + time;
    }

    static public String getLongTime() {
        Date date = new Date();
        return DateFormat.getTimeInstance(DateFormat.LONG).format(date);
    }

    static public void loadImage(Context context, String imageURL, ImageView imageView) {
        Glide.with(context).load(imageURL).into(imageView);
    }

    static public void showToast(Context context, String message) {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }
}
