/*
 * Created by Wisam Naji on 11/4/17 10:43 AM.
 * Copyright (c) 2017. All rights reserved.
 * Copying, redistribution or usage of material used in this file is free for educational purposes ONLY and should not be used in profitable context.
 *
 * Last modified on 11/4/17 10:43 AM
 */

package com.recoded.tasksnotifier;

import android.provider.BaseColumns;

/**
 * Created by wisam on Nov 4 17.
 */

public final class TasksContract {

    public static final String TABLE_NAME = "tasks";
    private TasksContract(){}

    public static class TasksTable implements BaseColumns{
        public final static String ID = BaseColumns._ID;
        public final static String TITLE = "title";
        public final static String BODY = "body";
        public final static String DONE = "done";
        public final static String LAST_MODIFIED = "lastModified";
        public final static String DONE_ON = "doneOn";
        public final static String CREATED_ON = "createdOn";

        public final static String[] ALL_FIELDS = {ID, TITLE, BODY, DONE, DONE_ON, LAST_MODIFIED, CREATED_ON};

    }
}
