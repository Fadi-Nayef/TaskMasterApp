package com.example.taskmasterapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskItem.class}, version = 1)
public abstract class AppDB  extends RoomDatabase {
    public abstract TasksDao tasksDao();
}
