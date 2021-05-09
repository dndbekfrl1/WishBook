package com.jina.wishbook.WishList;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int bookCover;
    private String bookTitle;

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookCover() {
        return bookCover;
    }

    public void setBookCover(int bookCover) {
        this.bookCover = bookCover;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
