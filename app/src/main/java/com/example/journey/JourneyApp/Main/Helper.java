package com.example.journey.JourneyApp.Main;

import com.example.journey.JourneyApp.Profile.Models.ProfileTodoItemModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Helper {



    public static String todoISOToDate(String isoString) {
        DateTimeFormatter isoToDateFormat = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime date = LocalDateTime.parse(isoString, isoToDateFormat);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return dateFormat.format(date);
    }

    public static ArrayList<ProfileTodoItemModel> getCompletedTasks(ArrayList<ProfileTodoItemModel> items) {
        return items.stream().filter(ProfileTodoItemModel::getCompleted).collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<ProfileTodoItemModel> getToBeCompletedTasks(ArrayList<ProfileTodoItemModel> items) {
        return items.stream().filter(task -> !task.getCompleted()).collect(Collectors.toCollection(ArrayList::new));
    }
}
