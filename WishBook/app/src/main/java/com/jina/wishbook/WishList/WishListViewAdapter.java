package com.jina.wishbook.WishList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jina.wishbook.Database.Book;
import com.jina.wishbook.R;

import java.util.List;


public class WishListViewAdapter extends RecyclerView.Adapter<WishListViewAdapter.CustomViewHolder> {

    private List<Book> listViewItemList = null;
    private int NumOfdata=0;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView bookTitle;
        protected ImageView bookCover;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.bookTitle=view.findViewById(R.id.book_title);
            this.bookCover=view.findViewById(R.id.book_cover);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("Recyclerview", "position = "+ getAdapterPosition());
                    //TODO: 롱클릭 -> 삭제/구매
                    return false;
                }
            });
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
        holder.bookCover.setImageResource(listViewItemList.get(position).bookCover);
        holder.bookTitle.setText(listViewItemList.get(position).bookTitle);
    }

    @Override
    public int getItemCount() {
        return NumOfdata;
    }

    public void setListViewItemList(List<Book> books){
        listViewItemList=books;
        NumOfdata=listViewItemList.size();
        Log.e("dddddDDD",""+NumOfdata);
        notifyDataSetChanged();
    }



}
