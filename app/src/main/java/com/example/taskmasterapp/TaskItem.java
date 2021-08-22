package com.example.taskmasterapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class TaskItem {


    @PrimaryKey(autoGenerate = true)
private Long id;
@ColumnInfo(name = "task_title")
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskItem(String taskTitle, String taskBody, String taskStatus) {
        this.taskTitle = taskTitle;
        this.taskBody = taskBody;
        this.taskStatus = taskStatus;
    }

}
