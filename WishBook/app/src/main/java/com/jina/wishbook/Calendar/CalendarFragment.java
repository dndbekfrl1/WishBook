package com.jina.wishbook.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
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

import com.jina.wishbook.Camera.CameraActivity;
import com.jina.wishbook.R;

import java.util.ArrayList;

import java.util.Calendar;


public class CalendarFragment extends Fragment {
    //TODO: 구매 선택시 캘린더에 반영

    Toolbar toolbar;
    GridView gridView;
    DateAdapter adapter;
    ArrayList<CalData> arrData;
    Calendar mCal;
    Calendar mCalToday;
    TextView dateTitle;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setCalendarDate(int month, GridView gridView){
        Log.e("", String.valueOf(Calendar.YEAR));


        arrData= new ArrayList<CalData>();

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

        dateTitle.setText(mCal.get(Calendar.YEAR)+"년 "+(mCal.get(Calendar.MONTH) + 1)+"월");

        adapter = new DateAdapter(this.getContext(),arrData);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_camera,menu);
    }


    //카메라 스캔 실행
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera_menu:
                Intent intent = new Intent(this.getActivity(),CameraActivity.class);
                startActivity(intent);
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("여기는 캘린더");



        mCalToday = Calendar.getInstance();
        mCal = Calendar.getInstance();
        gridView = view.findViewById(R.id.calendar_grid);
        dateTitle = view.findViewById(R.id.date_title);

        setCalendarDate(mCal.get(Calendar.MONTH)+1,gridView);

        return view;
    }
}

class DateAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CalData> arrData;
    private LayoutInflater inflater;

    public DateAdapter(Context c, ArrayList<CalData> arr) {
        this.context = c;
        this.arrData = arr;
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
            if(arrData.get(position).getDayofweek() == 1)
            {
                ViewText.setTextColor(Color.RED);
            }
            else if(arrData.get(position).getDayofweek() == 7)
            {
                ViewText.setTextColor(Color.BLUE);
            }
            else
            {
                ViewText.setTextColor(Color.BLACK);
            }
        }

        return convertView;

    }

}
