package com.jina.wishbook.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

@Database(entities = {Book.class},version = 1,exportSchema = false)
public abstract class BookDatabase extends RoomDatabase{
    abstract public BookDAO bookDAO();

    private  static BookDatabase INSTANCE;

    public  static BookDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, BookDatabase.class,"table_book").build();
        }
        return INSTANCE;
    }


    public  static void destroyInstance(){
        INSTANCE=null;
    }
}

