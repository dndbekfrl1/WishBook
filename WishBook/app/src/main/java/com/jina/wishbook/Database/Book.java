package com.jina.wishbook.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="book")
public class Book {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "book_title")
    public String bookTitle;
    @ColumnInfo(name="author")
    public String author;
    @ColumnInfo(name="add_date")
    public String date;
    @ColumnInfo(name="book_cover")
    public int bookCover;


}
