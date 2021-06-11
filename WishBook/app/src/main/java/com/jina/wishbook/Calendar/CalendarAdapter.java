package com.jina.wishbook.Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jina.wishbook.R;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CalData>arrData;
    private LayoutInflater inflater;

    public CalendarAdapter(Context c, ArrayList<CalData> arr){
        this.context=c;
        this.arrData=arr;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
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