package com.example.instanote.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.instanote.Adapter.NotesAdapter;
import com.example.instanote.Database.RoomDB;
import com.example.instanote.Models.NotesModel;
import com.example.instanote.Interface.NotesClickListener;
import com.example.instanote.R;
import com.example.instanote.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private ActivityMainBinding binder;
    NotesAdapter adapter;
    RoomDB database;
    NotesModel selected_note;
    List<NotesModel> list=new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();

        database=RoomDB.getInstance(this);
        list= database.mainDAO().getall();

        updateRV();

        binder.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivityForResult(intent,101);
            }
        });

        binder.svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter_note(newText);
                return true;
            }
        });
    }

    private void filter_note(String text) {
        List<NotesModel> filtered_notes=new ArrayList<>();
        for(NotesModel cur_note:list)
        {
            if(cur_note.getTitle().toLowerCase().contains(text.toLowerCase())
            || cur_note.getNote().toLowerCase().contains(text.toLowerCase()))
            {
                filtered_notes.add(cur_note);
            }
        }
        adapter.filter_list(filtered_notes);
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
        else if(requestCode==102)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                NotesModel new_note=(NotesModel) data.getSerializableExtra("note");
                database.mainDAO().update(new_note.getId(),new_note.getTitle(),new_note.getNote());
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
            Intent intent=new Intent(MainActivity.this,CreateNoteActivity.class);
            intent.putExtra("old_note",note);
            startActivityForResult(intent,102);
        }

        @Override
        public void onLongClick(NotesModel note, CardView cardView) {
            selected_note=new NotesModel();
            selected_note=note;
            show_popup(cardView);
        }
    };

    private void show_popup(CardView cardView) {
        PopupMenu popupMenu=new PopupMenu(this,cardView);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.pin:
            {
                if(selected_note.isPinned())
                {
                    database.mainDAO().pin(selected_note.getId(),false);
                    Toast.makeText(this, "Note unpinned!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.mainDAO().pin(selected_note.getId(),true);
                    Toast.makeText(this, "Note pinned!", Toast.LENGTH_SHORT).show();
                }
                list.clear();
                list.addAll(database.mainDAO().getall());
                adapter.notifyDataSetChanged();
                return true;
            }
            case R.id.delete:
            {
                database.mainDAO().delete(selected_note);
                list.remove(selected_note);
                adapter.notifyDataSetChanged();
                return true;
            }
            default: return false;
        }
    }
}