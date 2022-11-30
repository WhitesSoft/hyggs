package com.hyggs.clientchat.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.Dash;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ManagerSharedPreferences managerSharedPreferences;
    private boolean wasSent = false;
    private SplashActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        managerSharedPreferences = new ManagerSharedPreferences(this);
        self = this;
        //Status bar white color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        //Preferences depending on version
        try {
            ManagerSharedPreferences manageSharedPreferences = new ManagerSharedPreferences(this);
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionAppCurrent = pInfo.versionName;
            String versionApp = manageSharedPreferences.getVersionApp();
            if (versionApp != null) {
                if (!versionApp.equals(versionAppCurrent)) {
                    manageSharedPreferences.clearAll();
                    manageSharedPreferences.setVersionApp(versionAppCurrent);
                }
            } else {
                manageSharedPreferences.clearAll();
                manageSharedPreferences.setVersionApp(versionAppCurrent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Start method, count down splash
        startApp();
    }

    private void startApp() {
        try {
            Thread timerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        Log.e("Hyggs", "Splash", e);
                    } finally {
                        //When user already logged, send them to dashboard activity
                        if (managerSharedPreferences.getLogged()) {
//                            Toast.makeText(self, "Next activity here.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SplashActivity.this, HomeChatActivity.class));
                        } else {
                            Intent intent = new Intent(SplashActivity.this, SlidersActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            };
            timerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}