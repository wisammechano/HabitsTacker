/*
 * Created by Wisam Naji on 10/31/17 5:43 PM.
 * Copyright (c) 2017. All rights reserved.
 * Copying, redistribution or usage of material used in this file is free for educational purposes ONLY and should not be used in profitable context.
 *
 * Last modified on 10/31/17 5:25 PM
 */

package com.recoded.tasksnotifier;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.recoded.tasksnotifier.TasksContract.TasksTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TasksHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new TasksHelper(this);
        insertNewTask(1 + Math.round((float) Math.random()*10/2));
        readTasks();
    }

    private void readTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TasksContract.TABLE_NAME, TasksTable.ALL_FIELDS, null, null, null, null, TasksTable.TITLE);
        ArrayList<Task> tasks = new ArrayList<>();

        while (c.moveToNext()){
            String title = c.getString(c.getColumnIndex(TasksTable.TITLE));
            String body = c.getString(c.getColumnIndex(TasksTable.BODY));
            boolean done = c.getInt(c.getColumnIndex(TasksTable.DONE)) != 0;

            Task task = new Task(title,body,done);

            task.setLastModified(c.getString(c.getColumnIndex(TasksTable.LAST_MODIFIED)));
            task.setDoneOn(c.getString(c.getColumnIndex(TasksTable.DONE_ON)));
            task.setId(c.getInt(c.getColumnIndex(TasksTable.ID)));
            task.setCreatedOn(c.getString(c.getColumnIndex(TasksTable.CREATED_ON)));

            tasks.add(task);
        }
        c.close();
        db.close();
    }

    private void insertNewTask(int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        while (count >=0){
            Task task = new Task("Task" + count, "This is a test body for task no." + count, count%2 == 0);
            db.insert(TasksContract.TABLE_NAME, null, task.getInsertContentValues());
            count--;
        }
        db.close();
    }
}
