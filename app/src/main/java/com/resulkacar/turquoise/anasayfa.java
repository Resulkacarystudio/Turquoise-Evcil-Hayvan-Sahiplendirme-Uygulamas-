package com.resulkacar.turquoise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class anasayfa extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    AramaFragment akisFragment = new AramaFragment();
    akisFragment  aramaFragment= new akisFragment();
    ProfilFragment profilFragment = new ProfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);


        bottomNavigationView = findViewById(R.id.bottom_nagivation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,akisFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notifacition);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if(item.getItemId() == R.id.home)
               {
                   getSupportFragmentManager().beginTransaction().replace(R.id.container,akisFragment).commit();
                   return true;

               }
               else if(item.getItemId() == R.id.notifacition)
               {
                   getSupportFragmentManager().beginTransaction().replace(R.id.container,aramaFragment).commit();
                   return true;

               }
               else if(item.getItemId() == R.id.setting)
               {
                   getSupportFragmentManager().beginTransaction().replace(R.id.container,profilFragment).commit();
                   return true;

               }

                return false;
            }
        });





    }
}