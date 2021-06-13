package com.jina.wishbook.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="table_book")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String bookTitle;
    @ColumnInfo(name="author")
    public String author;
    @ColumnInfo(name="date") //구매한 날짜를 가리킴
    public String date;
    @ColumnInfo(name = "bought") //구매여부
    public int bought;
    @ColumnInfo(name="cover")
    public String bookCover;


}
