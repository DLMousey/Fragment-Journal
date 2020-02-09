package com.deadlyllama.journal.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.deadlyllama.journal.JournalDatabase;
import com.deadlyllama.journal.dao.EntryDao;
import com.deadlyllama.journal.entity.Entry;

import java.util.List;

public class EntryRepository {

    private EntryDao entryDao;
    private LiveData<List<Entry>> allEntries;

    public EntryRepository(Application application) {
        JournalDatabase db = JournalDatabase.getDatabase(application);
        entryDao = db.entryDao();
        allEntries = entryDao.getAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public void insert(Entry entry) {
        new insertAsyncTask(entryDao).execute(entry);
    }

    public void update(Entry entry) {
        new updateAsyncTask(entryDao).execute(entry);
    }

    public void delete(Entry entry) {
        new deleteAsyncTask(entryDao).execute(entry);
    }

    private static class insertAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao asyncEntryDao;

        insertAsyncTask(EntryDao dao) {
            asyncEntryDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            asyncEntryDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao asyncEntryDao;

        updateAsyncTask(EntryDao dao) {
            asyncEntryDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            asyncEntryDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao asyncEntryDao;

        deleteAsyncTask(EntryDao dao) {
            asyncEntryDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            asyncEntryDao.delete(params[0]);
            return null;
        }
    }
}
