package com.jina.wishbook.Camera;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jina.wishbook.BookAPI.BookAPI;
import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDAO;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;

import java.util.ArrayList;

public class BottomSheet extends BottomSheetDialogFragment {
    static WebView webView;
    private WebSettings webSettings;
    private Button addWish;
    static String bookTitle;
    static String bookLink;
    static String bookAuthor;
    static String bookCover;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog,container,false);

        bookTitle = getArguments().getString("bookTitle");
        webView = v.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        SearchAPI searchAPI = new SearchAPI();
        searchAPI.execute(bookTitle);

        addWish =v.findViewById(R.id.btn_add_wish);
        addWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBookDB();
                addWish.setBackground(getResources().getDrawable(R.drawable.btn_shape));
                addWish.setBackgroundColor(getResources().getColor(R.color.point));
                addWish.setTextColor(getResources().getColor(R.color.white));
                addWish.setText("위시에 담았습니다.");
            }
        });

        return v;
    }

    public void  insertBookDB(){
        int cover = R.drawable.book1;

        Book book = new Book();
        book.bookTitle=bookTitle;
        book.author=bookAuthor;
        book.bookCover=bookCover;

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


    public static class SearchAPI extends AsyncTask<String, String, ArrayList<String>> {
        BookAPI bookAPI;

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String book_title = strings[0];
            bookAPI = new BookAPI(book_title);
            return bookAPI.searchAPI();
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);

            bookTitle = s.get(0);
            bookLink = s.get(1);
            bookAuthor = s.get(2);
            bookCover = s.get(3);

            webView.loadUrl(bookLink);
        }
    }


}

