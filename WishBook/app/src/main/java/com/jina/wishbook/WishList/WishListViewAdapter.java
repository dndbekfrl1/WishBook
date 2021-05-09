package com.jina.wishbook.WishList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jina.wishbook.R;

import java.util.ArrayList;


public class WishListViewAdapter extends RecyclerView.Adapter<WishListViewAdapter.CustomViewHolder> {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public WishListViewAdapter(ArrayList<ListViewItem> list){
        this.listViewItemList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView bookTitle;
        protected ImageView bookCover;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.bookTitle=view.findViewById(R.id.book_title);
            this.bookCover=view.findViewById(R.id.book_cover);
        }
    }

    @NonNull
    @Override
    public WishListViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewAdapter.CustomViewHolder holder, int position) {
        holder.bookCover.setImageResource(listViewItemList.get(position).getBookCover());
        holder.bookTitle.setText(listViewItemList.get(position).getBookTitle());
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }


}
