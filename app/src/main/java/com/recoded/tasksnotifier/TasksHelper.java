/*
 * Created by Wisam Naji on 11/4/17 11:00 AM.
 * Copyright (c) 2017. All rights reserved.
 * Copying, redistribution or usage of material used in this file is free for educational purposes ONLY and should not be used in profitable context.
 *
 * Last modified on 11/4/17 11:00 AM
 */

package com.recoded.tasksnotifier;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.recoded.tasksnotifier.TasksContract.TasksTable;


/**
 * Created by wisam on Nov 4 17.
 */

public class TasksHelper extends SQLiteOpenHelper {

    public TasksHelper(Context ctx){
        super(ctx, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TasksContract.TABLE_NAME
                + "(" + TasksTable.ID + " INTEGER PRIMARY KEY,"
                + TasksTable.TITLE + " TEXT,"
                + TasksTable.BODY + " TEXT,"
                + TasksTable.DONE + " INTEGER,"
                + TasksTable.DONE_ON + " TEXT,"
                + TasksTable.LAST_MODIFIED + " TEXT,"
                + TasksTable.CREATED_ON + " TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
