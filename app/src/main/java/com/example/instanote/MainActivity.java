package com.example.instanote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.instanote.Adapter.NotesAdapter;
import com.example.instanote.Database.MainDAO;
import com.example.instanote.Database.RoomDB;
import com.example.instanote.Models.NotesModel;
import com.example.instanote.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binder;
    NotesAdapter adapter;
    RoomDB database;
    List<NotesModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        database=RoomDB.getInstance(this);
        list= database.mainDAO().getall();

        updateRV();

        binder.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateNoteActivity.class);
                startActivityForResult(intent,101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                NotesModel new_note=(NotesModel) data.getSerializableExtra("note");
                database.mainDAO().insert(new_note);
                list.clear();
                list.addAll(database.mainDAO().getall());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRV() {
        binder.rvMain.setHasFixedSize(true);
        binder.rvMain.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter=new NotesAdapter(MainActivity.this,list,notesClickListener);
        binder.rvMain.setAdapter(adapter);
    }
    private final NotesClickListener notesClickListener=new NotesClickListener() {
        @Override
        public void onClick(NotesModel note) {

        }

        @Override
        public void onLongClick(NotesModel note, CardView cardView) {

        }
    };
}