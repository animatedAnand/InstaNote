package com.example.instanote.Interface;

import androidx.cardview.widget.CardView;

import com.example.instanote.Models.NotesModel;

public interface NotesClickListener {
    void onClick(NotesModel note);
    void onLongClick(NotesModel note, CardView cardView);
}
