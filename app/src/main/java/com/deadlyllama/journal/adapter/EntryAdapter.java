package com.deadlyllama.journal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deadlyllama.journal.R;
import com.deadlyllama.journal.entity.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private final LayoutInflater inflater;
    private EntryViewHolder activeViewHolder;

    private List<Entry> allEntries;

    public EntryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_entryitem, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        activeViewHolder = holder;

        if (allEntries != null) {
            Entry current = allEntries.get(position);
            holder.titleTextView.setText(current.getTitle());

            if (current.getContent().length() > 220) {
                String preview = current.getContent().substring(0, 220);
                preview += "...";
                holder.contentTextView.setText(preview);
            } else {
                holder.contentTextView.setText(current.getContent());
            }

            holder.contentTextView.setText(current.getContent());
            holder.dateTextView.setText(current.getCreatedAt().toString());
        } else {
            Log.d("COMDEADLYLLAMAJOURNAL", "onBindViewHolder: allentries is null!");
        }
    }

    public void setAllEntries(List<Entry> entries) {
        allEntries = entries;
    }

    @Override
    public int getItemCount() {
        if (allEntries != null) {
            return allEntries.size();
        } else {
            return 0;
        }
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView contentTextView;
        private final TextView dateTextView;

        private EntryViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.entryitem_title_textview);
            contentTextView = itemView.findViewById(R.id.entryitem_content_textview);
            dateTextView = itemView.findViewById(R.id.entryitem_date_textview);
        }
    }

}
