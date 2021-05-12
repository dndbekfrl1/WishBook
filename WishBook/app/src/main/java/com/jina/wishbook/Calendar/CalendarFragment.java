package com.jina.wishbook.Calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.jina.wishbook.Camera.CameraActivity;
import com.jina.wishbook.R;



public class CalendarFragment extends Fragment {

    Toolbar toolbar;

    LinearLayout header;
    TextView txtDateDay;
    GridView gridView;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_camera,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera_menu:
                Intent intent = new Intent(this.getActivity(),CameraActivity.class);
                startActivity(intent);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flagment_container,cameraFragment).commit();
//                return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("여기는 캘린더");

        return view;
    }
}
