package com.jina.wishbook.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.jina.wishbook.Camera.CameraActivity;
import com.jina.wishbook.Database.Book;
import com.jina.wishbook.Database.BookDatabase;
import com.jina.wishbook.R;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {
    private Toolbar toolbar;
    private GridView gridView;
    private DateAdapter adapter;
    private ArrayList<CalData> arrData;
    private Calendar mCal;
    private Calendar mCalToday;
    private TextView dateTitle;
    private List<Book> curmonthBooks;
    private int[] dateArr;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setCalendarDate(int month, GridView gridView, List<Book> books){
        curmonthBooks = new ArrayList<>();
        arrData = new ArrayList<CalData>();
        dateArr = new int[32];

        mCalToday.set(mCal.get(Calendar.YEAR), month-1,1);
        int startday = mCalToday.get(Calendar.DAY_OF_WEEK);
        if(startday != 1)
        {
            for(int i=0; i<startday-1; i++)
            {
                arrData.add(null);
            }
        }

        mCal.set(Calendar.MONTH, month-1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            mCalToday.set(mCal.get(Calendar.YEAR), month-1, (i+1));
            arrData.add(new CalData((i+1), mCalToday.get(Calendar.DAY_OF_WEEK)));
        }

        dateTitle.setText(mCal.get(Calendar.YEAR)+"년 "+month+"월");

        //달에 맞는 책 추가 & 구매한 날짜 저장
        for(Book book : books){
           int m = Integer.parseInt(book.date.split("-")[1]);
           Log.e("m",m+"");
           if(m == month){
               curmonthBooks.add(book);
               dateArr[Integer.parseInt(book.date.split("-")[2])] =1;
            }
        }

        adapter = new DateAdapter(this.getContext(),arrData,dateArr);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_camera,menu);
        Log.e("oncreateoptionsmenu","DD");
    }

    //카메라 스캔 실행
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera_menu:
                Intent intent = new Intent(this.getActivity(),CameraActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("여기는 캘린더");

        mCalToday = Calendar.getInstance();
        mCal = Calendar.getInstance();
        gridView = view.findViewById(R.id.calendar_grid);
        dateTitle = view.findViewById(R.id.date_title);

        BookDatabase db = BookDatabase.getDatabase(this.getContext());
        db.bookDAO().getBoughtAll().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                Log.e("list size",""+books.size());
                setCalendarDate(mCal.get(Calendar.MONTH)+1,gridView,books);
            }
        });

        return view;
    }
}

class DateAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CalData> arrData;
    private LayoutInflater inflater;
    private int[] dateArr;


    public DateAdapter(Context c, ArrayList<CalData> arr, int[] dateArr) {
        this.context = c;
        this.arrData = arr;
        this.dateArr = dateArr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calendar_viewitem, parent, false);
        }

        TextView ViewText = (TextView)convertView.findViewById(R.id.ViewText);

        if(arrData.get(position) == null)
            ViewText.setText("");

        else
        {
            ViewText.setText(arrData.get(position).getDay()+"");

            //구매한 날짜를 표시함
            if(dateArr[arrData.get(position).getDay()]==1){
                convertView = inflater.inflate(R.layout.calendar_viewitem_event,parent,false);
                ViewText=(TextView)convertView.findViewById(R.id.ViewText);
                ViewText.setText(arrData.get(position).getDay()+"");
            }

            if(arrData.get(position).getDayofweek() == 1 || arrData.get(position).getDayofweek() == 7)
            {
                ViewText.setTextColor(R.color.dark_gray);
            }
            else
            {
                ViewText.setTextColor(Color.BLACK);
            }
        }

        return convertView;

    }

}
