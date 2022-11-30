package com.hyggs.clientchat.managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.dialogs.CustomProgressDialog;

public class ManagerProgressDialog {

    private Context context;
    private ProgressDialog progress;
    private ProgressDialog customProgressDialog;

    public ManagerProgressDialog(Context context) {
        this.context = context;
        customProgressDialog = CustomProgressDialog.ctor(context, R.style.ThemeProgressDialog);
        customProgressDialog.setMessage("Loading...");
    }

    public synchronized void showProgress(){
        showProgress(null);
    }

    public synchronized void showProgress(String message){
        if (message!=null) {
            customProgressDialog.setMessage(message);
        }
        try{
            customProgressDialog.show();
//            progress.show();
        }catch (Exception e){
            Log.e("error dismiss ", e.getMessage());
        }
    }

    public synchronized void dismissProgress(){
        if (customProgressDialog!= null && customProgressDialog.isShowing() ){
            try {
                customProgressDialog.dismiss();
//                progress.dismiss();
            }catch (Exception e){
                Log.e("error dismiss ", e.getMessage());
            }
        }
    }
}
