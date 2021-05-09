package com.jina.wishbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jina.wishbook.Calendar.CalendarFragment;
import com.jina.wishbook.WishList.WishFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private CameraFragment cameraFragment = new CameraFragment();
    private WishFragment wishFragment = new WishFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flagment_container,calendarFragment).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.camera:
                                fragmentTransaction.replace(R.id.flagment_container, cameraFragment).commit();
                                return true;
                            case R.id.wish:
                                fragmentTransaction.replace(R.id.flagment_container, wishFragment).commit();
                                return true;
                            case R.id.home:
                                fragmentTransaction.replace(R.id.flagment_container,calendarFragment).commit();
                        }
                        return false;
                    }

                });

    }


}
