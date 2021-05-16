package com.jina.wishbook.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class},version = 1)
public abstract class BookDatabase extends RoomDatabase{
    abstract public BookDAO bookDAO();

    private  static BookDatabase INSTANCE;

    public  static BookDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, BookDatabase.class,"book").build();
        }
        return INSTANCE;
    }

    public  static void destroyInstance(){
        INSTANCE=null;
    }
}
