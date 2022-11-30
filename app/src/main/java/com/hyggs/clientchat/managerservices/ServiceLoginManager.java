package com.hyggs.clientchat.managerservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.LoginActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.models.LoginResponse;
import com.hyggs.clientchat.utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLoginManager implements Callback<LoginResponse> {

    private Context context;
    private ManagerProgressDialog progress;
    private LoginActivity activity;

    public ServiceLoginManager(Context context, LoginActivity activity) {
        this.context = context;
        this.activity = activity;
        progress = new ManagerProgressDialog(context);
    }

    public void loginUser(String email, String pasdword) {
        if (Utilities.isConnected(context)) {
            progress.showProgress();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Provider.urlMain)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitInterface retrofitIR = retrofit.create(RetrofitInterface.class);

            Call<LoginResponse> call;
            call = retrofitIR.loginClient(Provider.KEY,
                    email,
                    pasdword,
                    "1",
                    "0.0.0.0",
                    Provider.idProject);

            call.enqueue(this);

        } else {

            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
            Log.i( "Conexion", "1");
        }
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

        int code = response.code();
        if (code == 200) {
            LoginResponse basicResponse = response.body();
            if(basicResponse!=null){
                activity.makeLogin(basicResponse);
            } else {
                Toast.makeText(context, "Login service error.", Toast.LENGTH_LONG).show();
            }
        } else {
            String basicResponse = null;
            try {
                basicResponse = response.errorBody().string();
                if(basicResponse!=null){
                    if(!basicResponse.isEmpty()) {
                        if(basicResponse.contains("Cliente no existe")){
                            activity.clientDoesntExist("Supplier does'nt exist, you must register.");
                        }
                    }
                } else {
                    Toast.makeText(context, "Login service error.", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progress.dismissProgress();
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        Toast.makeText(context, "Login service error.", Toast.LENGTH_LONG).show();
        progress.dismissProgress();
    }
}


