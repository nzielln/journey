package com.example.journey.JourneyApp.Main;

import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;

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

    public static ArrayList<TaskItemModel> getCompletedTasks(ArrayList<TaskItemModel> items) {
        return items.stream().filter(TaskItemModel::getCompleted).collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<TaskItemModel> getToBeCompletedTasks(ArrayList<TaskItemModel> items) {
        return items.stream().filter(task -> !task.getCompleted()).collect(Collectors.toCollection(ArrayList::new));
    }
}
