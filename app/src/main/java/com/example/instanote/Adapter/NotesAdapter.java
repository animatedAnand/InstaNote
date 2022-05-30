package com.example.instanote.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instanote.Models.NotesModel;
import com.example.instanote.NotesClickListener;
import com.example.instanote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<NotesModel> list;
    NotesClickListener listener;

    public NotesAdapter(Context context, List<NotesModel> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item_layout
        ,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesModel cur_note=list.get(position);
        holder.tv_title.setText(cur_note.getTitle());
        holder.tv_notes.setText(cur_note.getNote());
        holder.tv_time.setText(cur_note.getTime());
        if(cur_note.isPinned())
        {
            holder.iv_pin.setImageResource(R.drawable.ic_pin);
        }
        else
        {
            holder.iv_pin.setImageResource(0);
        }
        int color_code=getRandomColor();
        holder.cv_note_item.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));
    }
    private int getRandomColor()
    {
        List<Integer> color_code=new ArrayList<>();
        color_code.add(R.color.color1);
        color_code.add(R.color.color2);
        color_code.add(R.color.color3);
        color_code.add(R.color.color4);
        color_code.add(R.color.color5);
        color_code.add(R.color.color6);

        Random random=new Random();
        int random_color=random.nextInt(color_code.size());
        return color_code.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder
{
    CardView cv_note_item;
    TextView tv_title,tv_notes,tv_time;
    ImageView iv_pin;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        cv_note_item=itemView.findViewById(R.id.cv_note_item);
        tv_title=itemView.findViewById(R.id.tv_title);
        tv_notes=itemView.findViewById(R.id.tv_notes);
        tv_time=itemView.findViewById(R.id.tv_time);
        iv_pin=itemView.findViewById(R.id.iv_pin);
    }
}
