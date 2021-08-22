package com.example.taskmasterapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TasksDao {
@Insert
    void addTask(TaskItem task);

@Query("SELECT * FROM taskitem WHERE task_title LIKE :title")
    TaskItem findByName(String title);

@Query("SELECT * FROM taskitem")
    List<TaskItem> findAll();

}
