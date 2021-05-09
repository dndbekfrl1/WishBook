package com.jina.wishbook.WishList;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jina.wishbook.R;

import java.util.ArrayList;


public class WishFragment extends Fragment {
    private RecyclerView recyclerView ;
    private WishListViewAdapter adapter;
    private ArrayList<ListViewItem> listViewItems = new ArrayList<>();

    public WishFragment() {

    }

    public void initListViewItems(){
        ListViewItem item1 = new ListViewItem();
        item1.setBookTitle("helo");
        int img = R.drawable.book1;
        item1.setBookCover(img);
        listViewItems.add(item1);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        this.initListViewItems();

        recyclerView =  view.findViewById(R.id.list_wish);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WishListViewAdapter(listViewItems);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(),new LinearLayoutManager(this.getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }


}
