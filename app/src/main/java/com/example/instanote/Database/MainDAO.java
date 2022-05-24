package com.example.instanote.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.instanote.Models.NotesModel;

import java.util.List;

public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(NotesModel note);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<NotesModel> getall();

    @Query("UPDATE notes SET title= :title,note= :note WHERE id= :id")
    void update(int id,String title,String note);
    @Delete
    void delete(NotesModel note);
}
