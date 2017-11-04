/*
 * Created by Wisam Naji on 11/4/17 11:12 AM.
 * Copyright (c) 2017. All rights reserved.
 * Copying, redistribution or usage of material used in this file is free for educational purposes ONLY and should not be used in profitable context.
 *
 * Last modified on 11/4/17 11:12 AM
 */

package com.recoded.habitstracker;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wisam on Nov 4 17.
 */

public class Habit implements Parcelable {
    public static final Parcelable.Creator<Habit> CREATOR = new Parcelable.Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel source) {
            return new Habit(source);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };
    private final static String DATE_FORMAT = "EEE, MMM dd, yyyy";
    private final static String TIME_FORMAT = " 'at' HH:mm:ss";
    private int id;
    private String title;
    private String body;
    private boolean done;
    private Date doneOn, lastModified, createdOn;
    private ContentValues cvs;
    private DateFormat df;

    public Habit(String title, String body, boolean done) {
        this.title = title;
        this.body = body;
        this.id = 0;
        this.createdOn = Calendar.getInstance().getTime();
        this.done = done;
        this.doneOn = done? this.createdOn:null;
        this.lastModified = this.createdOn;
        cvs = new ContentValues();
        df = new SimpleDateFormat(DATE_FORMAT + TIME_FORMAT, Locale.US);
    }

    protected Habit(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.body = in.readString();
        this.done = in.readByte() != 0;
        long tmpDoneOn = in.readLong();
        this.doneOn = tmpDoneOn == -1 ? null : new Date(tmpDoneOn);
        long tmpLastModified = in.readLong();
        this.lastModified = tmpLastModified == -1 ? null : new Date(tmpLastModified);
        long tmpCreatedOn = in.readLong();
        this.createdOn = tmpCreatedOn == -1 ? null : new Date(tmpCreatedOn);
        this.cvs = in.readParcelable(ContentValues.class.getClassLoader());
        this.df = (DateFormat) in.readSerializable();
    }

    public static String getFriendlyTimeStamp(Date date) {
        long currentTime = Calendar.getInstance().getTime().getTime();
        DateFormat df = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        String time = df.format(date);
        long diff = currentTime - date.getTime();

        if (diff < (24 * 3600 * 1000)) {
            return "Today" + time;
        } else if (diff < (48 * 3600 * 1000)) {
            return "Yesterday" + time;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setLastModified(Calendar.getInstance().getTime());
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        setLastModified(Calendar.getInstance().getTime());
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        setLastModified(Calendar.getInstance().getTime());
        if (done) {
            setDoneOn(getLastModified(false));
        }
    }

    public String getDoneOn(boolean friendly) {
        if(doneOn == null) return "";
        String friendlyTimeStamp;
        if (friendly && ((friendlyTimeStamp = getFriendlyTimeStamp(doneOn)) != null))
            return friendlyTimeStamp;
        return df.format(doneOn);
    }

    public void setDoneOn(String doneOn) {
        try {
            this.doneOn = df.parse(doneOn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getLastModified(boolean friendly) {
        String friendlyTimeStamp;
        if (friendly && ((friendlyTimeStamp = getFriendlyTimeStamp(lastModified)) != null))
            return friendlyTimeStamp;
        return df.format(lastModified);
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setLastModified(String lastModified) {
        try {
            this.lastModified = df.parse(lastModified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getCreatedOn(boolean friendly) {
        String friendlyTimeStamp;
        if (friendly && ((friendlyTimeStamp = getFriendlyTimeStamp(createdOn)) != null))
            return friendlyTimeStamp;
        return df.format(createdOn);
    }

    public void setCreatedOn(String createdOn) {
        try {
            this.createdOn = df.parse(createdOn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ContentValues getInsertContentValues() {
        cvs.put(HabitsContract.TasksTable.TITLE, getTitle());
        cvs.put(HabitsContract.TasksTable.BODY, getBody());
        cvs.put(HabitsContract.TasksTable.DONE, isDone());
        cvs.put(HabitsContract.TasksTable.DONE_ON, getDoneOn(false));
        cvs.put(HabitsContract.TasksTable.LAST_MODIFIED, getLastModified(false));
        cvs.put(HabitsContract.TasksTable.CREATED_ON, getCreatedOn(false));
        return cvs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
        dest.writeLong(this.doneOn != null ? this.doneOn.getTime() : -1);
        dest.writeLong(this.lastModified != null ? this.lastModified.getTime() : -1);
        dest.writeLong(this.createdOn != null ? this.createdOn.getTime() : -1);
        dest.writeParcelable(this.cvs, flags);
        dest.writeSerializable(this.df);
    }
}
