package com.example.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {

    private OnItemClickListner listner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note>  DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.ttirle.setText(getItem(position).getTitle());
        holder.descrpt.setText(getItem(position).getDescription());
        holder.priot.setText(String.valueOf(getItem(position).getPriority()));
    }


    public Note getNoteAt(int po) {
        return getItem(po);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView ttirle, descrpt, priot;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            ttirle = itemView.findViewById(R.id.text_title);
            descrpt = itemView.findViewById(R.id.text_descript);
            priot = itemView.findViewById(R.id.text_piroty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int poss = getAdapterPosition();
                    if (listner != null && poss != RecyclerView.NO_POSITION)
                        listner.OnItemClick(getItem(poss));
                }
            });
        }


    }

    public interface OnItemClickListner {
        void OnItemClick(Note note);
    }

    public void OnitemClickListner(OnItemClickListner listner) {

        this.listner = listner;
    }

}
