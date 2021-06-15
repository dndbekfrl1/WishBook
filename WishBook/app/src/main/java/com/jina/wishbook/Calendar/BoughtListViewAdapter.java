package com.jina.wishbook.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;
import com.jina.wishbook.WishList.WishListViewAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoughtListViewAdapter extends RecyclerView.Adapter<BoughtListViewAdapter.CustomViewHolder> {
    private List<Book> listViewItemList = null;
    private int NumOfdata=0;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bought_list,parent,false);
        BoughtListViewAdapter.CustomViewHolder viewHolder = new BoughtListViewAdapter.CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Picasso.get().load(listViewItemList.get(position).bookCover).fit().into(holder.bookCover);
        holder.bookTitle.setText(listViewItemList.get(position).bookTitle);
        holder.bookAuthor.setText(listViewItemList.get(position).author);

        int bookId = listViewItemList.get(position).id;
        BookDatabase db = BookDatabase.getDatabase(holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return NumOfdata;
    }

    public void setListViewItemList(List<Book> books) {
        listViewItemList=books;
        NumOfdata=listViewItemList.size();
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView bookTitle;
        protected TextView bookAuthor;
        protected ImageView bookCover;


        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.bookTitle=view.findViewById(R.id.book_title_bought);
            this.bookAuthor=view.findViewById(R.id.book_author_bought);
            this.bookCover=view.findViewById(R.id.book_cover_bought);
        }
    }
}
