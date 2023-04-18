package com.example.journey.JourneyApp.Profile.Models;

import java.util.ArrayList;

public class TaskModel {
    String ID;
    String userID;
    ArrayList<TaskItemModel> completedTasks;
    ArrayList<TaskItemModel> todoTasks;

    public TaskModel(String ID, String userID, ArrayList<TaskItemModel> completedTasks, ArrayList<TaskItemModel> todoTasks) {
        this.ID = ID;
        this.userID = userID;
        this.completedTasks = completedTasks;
        this.todoTasks = todoTasks;
    }

    public TaskModel(String ID, String userID, ArrayList<TaskItemModel> tasks) {
        this.ID = ID;
        this.userID = userID;
        if (todoTasks.get(0).getCompleted()) {
            this.completedTasks = tasks;
        } else {
            this.todoTasks = tasks;
        }
    }

    public TaskModel(String ID, String userID) {
        this.ID = ID;
        this.userID = userID;
        this.completedTasks = new ArrayList<>();
        this.todoTasks = new ArrayList<>();
    }

    public TaskModel() {
       this.completedTasks = new ArrayList<>();
       this.todoTasks = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<TaskItemModel> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(ArrayList<TaskItemModel> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public ArrayList<TaskItemModel> getTodoTasks() {
        return todoTasks;
    }

    public void setTodoTasks(ArrayList<TaskItemModel> todoTasks) {
        this.todoTasks = todoTasks;
    }

    public void addCompleted(TaskItemModel taskItemModel) {
        completedTasks.add(taskItemModel);
    }

    public void addToDo(TaskItemModel taskItemModel) {
        todoTasks.add(taskItemModel);
    }

    public void removeFromCompleted(TaskItemModel taskItemModel) {
        completedTasks.removeIf(task -> task.getId().equals(taskItemModel.getId()));
    }

    public void removeFromToDo(TaskItemModel taskItemModel) {
        todoTasks.removeIf(task -> task.getId().equals(taskItemModel.getId()));
    }
}
