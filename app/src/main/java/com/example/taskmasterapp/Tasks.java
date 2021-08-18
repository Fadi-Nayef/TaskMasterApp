package com.example.taskmasterapp;

public class Tasks {

    private String taskTitle;
    private String taskBody;
    private String taskStatus;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskBody() {
        return taskBody;
    }

    public void setTaskBody(String taskBody) {
        this.taskBody = taskBody;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Tasks(String taskTitle, String taskBody, String taskStatus) {
        this.taskTitle = taskTitle;
        this.taskBody = taskBody;
        this.taskStatus = taskStatus;
    }

}
