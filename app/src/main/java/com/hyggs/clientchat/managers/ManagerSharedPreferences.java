package com.hyggs.clientchat.managers;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagerSharedPreferences {

    private final String KEY_USER_ID = "userIDHyggsAppClient";
    private final String KEY_VERSION_APP = "keyVersionAppHyggsAppClient";
    private final String KEY_LOGGED = "keyLoggedAppClient";
    private final String KEY_USER_PHONE = "keyUserPhone";
    private final String KEY_USER_EMAIL = "keyUserEmail";
    private final String KEY_USER_PASSWORD = "keyUserPassword";

    private Context mContext;

    public ManagerSharedPreferences(Context context) {
        mContext = context;
    }

    private SharedPreferences getSettings() {
        String SHARED_PREFS_FILE = "HyggsAppsPreferences";
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.clear();
        editor.commit();
    }


    //Save id user on local preferences
    public String getUserId() {
        return getSettings().getString(KEY_USER_ID, null);
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
    }

    //Save phone user on local preferences
    public String getUserPhone() {
        return getSettings().getString(KEY_USER_PHONE, null);
    }

    public void setUserPhone(String phone) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER_PHONE, phone);
        editor.commit();
    }

    //Save email user on local preferences
    public String getUserEmail() {
        return getSettings().getString(KEY_USER_EMAIL, null);
    }

    public void setUserEmail(String email) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }
    //Save password user on local preferences
    public String getUserPassword() {
        return getSettings().getString(KEY_USER_PASSWORD, "");
    }

    public void setUserPassword(String password) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER_PASSWORD, password);
        editor.commit();
    }

    //Save and get version app on local
    public String getVersionApp() {
        return getSettings().getString(KEY_VERSION_APP, null);
    }

    public void setVersionApp(String versionApp) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_VERSION_APP, versionApp);
        editor.commit();
    }

    public Boolean getLogged() {
        return getSettings().getBoolean(KEY_LOGGED, false);
    }

    public void setLogged(Boolean logged) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(KEY_LOGGED, logged);
        editor.commit();
    }






}
