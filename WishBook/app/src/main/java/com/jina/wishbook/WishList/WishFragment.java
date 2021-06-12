package com.jina.wishbook.WishList;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;

import java.util.ArrayList;
import java.util.List;


public class WishFragment extends Fragment {
    private RecyclerView recyclerView ;
    private WishListViewAdapter adapter;




    public WishFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        BookDatabase db = BookDatabase.getDatabase(this.getContext());

        recyclerView =  view.findViewById(R.id.list_wish);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WishListViewAdapter();
        db.bookDAO().getAll().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                Log.e("list size",""+books.size());
                adapter.setListViewItemList(books);
            }
        });
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(),new LinearLayoutManager(this.getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }


}
