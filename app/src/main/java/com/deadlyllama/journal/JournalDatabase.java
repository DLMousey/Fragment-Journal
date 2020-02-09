package com.deadlyllama.journal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.deadlyllama.journal.dao.EntryDao;
import com.deadlyllama.journal.entity.Entry;

import java.util.Date;
import java.util.List;

@Database(entities = {
        Entry.class
}, version = 1, exportSchema = false)
public abstract class JournalDatabase extends RoomDatabase {

    public abstract EntryDao entryDao();

    private static JournalDatabase INSTANCE;

    public static JournalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JournalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            JournalDatabase.class,
                            "journal_database"
                    ).fallbackToDestructiveMigration()
                     .addCallback(dbCallback)
                     .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final EntryDao entryDao;

        PopulateDbAsync(JournalDatabase db) {
            entryDao = db.entryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            entryDao.deleteAll();

            for (int i = 0 ; i < 25; i++) {
                entryDao.insert(new Entry("Test item " + i,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec semper fermentum ligula mattis porta. Phasellus eget nulla id arcu blandit vulputate. Proin pharetra tempor augue, vitae aliquam felis pulvinar non. ", new Date()));
            }

            return null;
        }
    }
}
