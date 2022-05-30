package com.example.instanote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instanote.Models.NotesModel;
import com.example.instanote.databinding.ActivityCreateNoteBinding;
import com.example.instanote.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {
        ActivityCreateNoteBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityCreateNoteBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        binder.ivSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=binder.etTitle.getText().toString();
                String note_content=binder.etNotes.getText().toString();
                if(note_content.isEmpty())
                {
                    binder.etNotes.setError("Empty");
                    binder.etNotes.requestFocus();
                    return;
                }
                SimpleDateFormat formatter=new SimpleDateFormat("EEE d MMM yyyy HH:mm a");
                Date date=new Date();
                NotesModel note=new NotesModel();
                note.setNote(note_content);
                note.setTime(formatter.format(date));
                note.setTitle(title);

                Intent intent=new Intent();
                intent.putExtra("note",note);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}