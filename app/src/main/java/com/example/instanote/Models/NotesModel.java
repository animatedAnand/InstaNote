package com.example.instanote.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "notes")
public class NotesModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id=0;
    @ColumnInfo(name = "title")
    String title="";

    @ColumnInfo(name = "note")
    String note="";

    @ColumnInfo(name = "time")
    String time="";
    @ColumnInfo(name = "pinned")
    boolean pinned=false;

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
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
