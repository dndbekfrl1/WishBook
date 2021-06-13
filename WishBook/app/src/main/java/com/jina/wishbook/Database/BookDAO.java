package com.jina.wishbook.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  void insertBook(Book ... books);

    @Query("UPDATE table_book SET date = :date, bought = :bought WHERE id=:id ")
    public  void updateBook(String date, int bought, int id);

    @Query("DELETE FROM table_book WHERE id == :id")
    public void deleteBook(int id);

    @Query("SELECT * FROM table_book WHERE bought = 0")
    public LiveData<List<Book>> getAll();

    @Query("SELECT * FROM table_book WHERE bought = 1")
    public LiveData<List<Book>> getBoughtAll();

}


