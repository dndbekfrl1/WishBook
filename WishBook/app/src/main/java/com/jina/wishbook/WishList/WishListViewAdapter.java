package com.jina.wishbook.WishList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDAO;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WishListViewAdapter extends RecyclerView.Adapter<WishListViewAdapter.CustomViewHolder> {

    private List<Book> listViewItemList = null;
    private int NumOfdata=0;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView bookTitle;
        protected TextView bookAuthor;
        protected ImageView bookCover;


        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.bookTitle=view.findViewById(R.id.book_title);
            this.bookAuthor=view.findViewById(R.id.book_author);
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
        Picasso.get().load(listViewItemList.get(position).bookCover).fit().into(holder.bookCover);
        holder.bookTitle.setText(listViewItemList.get(position).bookTitle);
        holder.bookAuthor.setText(listViewItemList.get(position).author);

        int bookId = listViewItemList.get(position).id;
        BookDatabase db = BookDatabase.getDatabase(holder.itemView.getContext());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());

                dlg.setTitle("책 설정변경").setMessage("선택하세요");
                dlg.setPositiveButton("구매완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String today = df.format(date);
                        int bought = 1;

                        new updateAsyncTask(db.bookDAO()).execute(today,bought,bookId);
                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new deletetAsyncTask(db.bookDAO()).execute(bookId);
                    }
                });

                dlg.create();
                dlg.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return NumOfdata;
    }

    public void setListViewItemList(List<Book> books){
        listViewItemList=books;
        NumOfdata=listViewItemList.size();
        notifyDataSetChanged();
    }

    public static class updateAsyncTask extends AsyncTask<Object, Void, Void> {
        private BookDAO bookDAO;
        public updateAsyncTask(BookDAO bookDAO){
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            String date = (String) objects[0];
            int bought = (int) objects[1];
            int id = (int) objects[2];

            bookDAO.updateBook(date, bought, id);
            return null;
        }
    }

    public static class deletetAsyncTask extends AsyncTask<Integer, Void, Void> {
        private BookDAO bookDAO;
        public deletetAsyncTask(BookDAO bookDAO){
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            bookDAO.deleteBook(id[0]);
            return null;
        }
    }


}
