package com.deadlyllama.journal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.deadlyllama.journal.entity.Entry;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry entry);

    @Query("DELETE FROM entry")
    void deleteAll();

    @Delete
    void delete(Entry entry);

    @Query("SELECT * FROM entry ORDER BY id DESC")
    LiveData<List<Entry>> getAllEntries();
}
