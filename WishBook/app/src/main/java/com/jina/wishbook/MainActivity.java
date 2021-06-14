package com.jina.wishbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.api.client.util.Value;
import com.jina.wishbook.Calendar.CalendarFragment;
import com.jina.wishbook.WishList.WishFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private WishFragment wishFragment = new WishFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();


    protected void checkPermission(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA))!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},0);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        SpannableStringBuilder str = new SpannableStringBuilder("나의 구매 기록");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,8,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(str);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);



        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flagment_container,calendarFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.wish:
                                SpannableStringBuilder str = new SpannableStringBuilder("나의 위시 목록");
                                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,8,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                toolbar.setTitle(str);
                                fragmentTransaction.replace(R.id.flagment_container, wishFragment).commit();
                                return true;
                            case R.id.home:
                                str = new SpannableStringBuilder("나의 구매 기록");
                                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,8,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                toolbar.setTitle(str);
                                fragmentTransaction.replace(R.id.flagment_container,calendarFragment).commit();
                                return true;
                        }
                        return false;
                    }

                });
    }

}
