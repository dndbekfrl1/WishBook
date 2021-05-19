package com.jina.wishbook.Camera;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDAO;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;

import java.util.Date;
import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    private WebView webView;
    private WebSettings webSettings;

    private Button addWish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog,container,false);

        addWish =v.findViewById(R.id.btn_add_wish);
        addWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleData();
                addWish.setText("위시에 담았습니다.");
            }
        });


        webView = v.findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient());
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.naver.com/");

        return v;
    }

    public void  sampleData(){
        String title = "demo title";
        String author="demo author";
        int cover = R.drawable.book1;
        String date = null;

        Book book = new Book();
        book.bookTitle=title;
        book.author=author;
        book.bookCover=cover;
        book.date=date;

        BookDatabase db = BookDatabase.getDatabase(this.getContext());
        new InsertAsyncTask(db.bookDAO()).execute(book);


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public static class InsertAsyncTask extends AsyncTask<Book, Void, Void>{
        private BookDAO bookDAO;
        public InsertAsyncTask(BookDAO bookDAO){
            this.bookDAO = bookDAO;
        }
        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.insertBook(books[0]);
            return null;
        }
    }
}

