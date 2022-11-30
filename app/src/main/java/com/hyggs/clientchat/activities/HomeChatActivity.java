package com.hyggs.clientchat.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.databinding.ActivityHomeChatBinding;
import com.hyggs.clientchat.dialogs.PopUpExit;

public class HomeChatActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    @SuppressLint("StaticFieldLeak")
    public static ActivityHomeChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        binding.icCallToolbar.setOnClickListener( v -> {
            drawer.openDrawer(GravityCompat.START);
            drawer.setScrimColor(Color.TRANSPARENT);
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chat, R.id.nav_service, R.id.nav_profile, R.id.nav_language, R.id.nav_exit)
                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.contentFragments);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = 0;
                switch (item.getItemId()){
                    case R.id.nav_chat:{
                        navController.navigate(R.id.nav_chat);
                        drawer.close();
                        id = item.getItemId();
                        break;
                    }
                    case R.id.nav_service:{
                        navController.navigate(R.id.nav_service);
                        drawer.close();
                        id = item.getItemId();
                        break;
                    }
                    case R.id.nav_profile:{
                        navController.navigate(R.id.nav_profile);
                        drawer.close();
                        id = item.getItemId();

                        break;
                    }
                    case R.id.nav_language:{
                        navController.navigate(R.id.nav_language);
                        drawer.close();
                        id = item.getItemId();
                        break;
                    }
                    case R.id.nav_exit:{

                        openLogOut();
                        item.setCheckable(false);
                        item.setChecked(false);
                        break;
                    }
                }

                return true;
            }
        });
    }
    public void openLogOut() {
        DialogFragment newFragment = new PopUpExit();
        newFragment.show(getSupportFragmentManager(), "logout");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.contentFragments);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}