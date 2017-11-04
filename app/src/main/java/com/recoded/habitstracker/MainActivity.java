/*
 * Created by Wisam Naji on 10/31/17 5:43 PM.
 * Copyright (c) 2017. All rights reserved.
 * Copying, redistribution or usage of material used in this file is free for educational purposes ONLY and should not be used in profitable context.
 *
 * Last modified on 10/31/17 5:25 PM
 */

package com.recoded.habitstracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recoded.habitstracker.HabitsContract.TasksTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HabitsHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new HabitsHelper(this);
        insertNewTask(1 + Math.round((float) Math.random()*10/2));
        readTasks();
    }

    private void readTasks() {
        cursor = getCursor();
        ArrayList<Habit> habits = new ArrayList<>();

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(TasksTable.TITLE));
            String body = cursor.getString(cursor.getColumnIndex(TasksTable.BODY));
            boolean done = cursor.getInt(cursor.getColumnIndex(TasksTable.DONE)) != 0;

            Habit habit = new Habit(title, body, done);

            habit.setLastModified(cursor.getString(cursor.getColumnIndex(TasksTable.LAST_MODIFIED)));
            habit.setDoneOn(cursor.getString(cursor.getColumnIndex(TasksTable.DONE_ON)));
            habit.setId(cursor.getInt(cursor.getColumnIndex(TasksTable._ID)));
            habit.setCreatedOn(cursor.getString(cursor.getColumnIndex(TasksTable.CREATED_ON)));

            habits.add(habit);
        }
    }


    private Cursor getCursor() {
        db = dbHelper.getReadableDatabase();
        return db.query(HabitsContract.TABLE_NAME, TasksTable.ALL_FIELDS, null, null, null, null, TasksTable.TITLE);
    }

    private void insertNewTask(int count) {
        db = dbHelper.getWritableDatabase();
        while (count >=0){
            Habit habit = new Habit("Habit" + count, "This is a test body for habit no." + count, count % 2 == 0);
            db.insert(HabitsContract.TABLE_NAME, null, habit.getInsertContentValues());
            count--;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
