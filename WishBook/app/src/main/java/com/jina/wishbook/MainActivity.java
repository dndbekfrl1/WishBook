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
import android.view.MenuItem;
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
                                toolbar.setTitle("나의 위시 목록");
                                setSupportActionBar(toolbar);
                                fragmentTransaction.replace(R.id.flagment_container, wishFragment).commit();
                                return true;
                            case R.id.home:
                                toolbar.setTitle("나의 구매 기록");
                                setSupportActionBar(toolbar);
                                fragmentTransaction.replace(R.id.flagment_container,calendarFragment).commit();
                                return true;
                        }
                        return false;
                    }

                });
    }
}
