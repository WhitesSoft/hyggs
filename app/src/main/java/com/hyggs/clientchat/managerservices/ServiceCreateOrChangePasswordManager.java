package com.hyggs.clientchat.managerservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.PasswordCreationAndRecoveryActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.models.ChangeOrCreatePassResponse;
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

public class ServiceCreateOrChangePasswordManager implements Callback<ChangeOrCreatePassResponse> {

    private Context context;
    private ManagerProgressDialog progress;
    private PasswordCreationAndRecoveryActivity activity;
    private String passwordToChange;

    public ServiceCreateOrChangePasswordManager(Context context, PasswordCreationAndRecoveryActivity activity) {
        this.context = context;
        this.activity = activity;
        progress = new ManagerProgressDialog(context);
    }

    public void changeOrCreatePassword(String pass,
                          String userId,
                          String token) {
        if (Utilities.isConnected(context)) {
            progress.showProgress();
            passwordToChange=pass;
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

            Call<ChangeOrCreatePassResponse> call;
            call = retrofitIR.changePassword(Provider.KEY,
                    userId,
                    token,
                    pass,
                    Provider.idProject);

            call.enqueue(this);
        } else {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
            Log.i( "Conexion", "1");
        }
    }

    @Override
    public void onResponse(Call<ChangeOrCreatePassResponse> call, Response<ChangeOrCreatePassResponse> response) {
        int code = response.code();
        if (code == 200) {
            ChangeOrCreatePassResponse basicResponse = response.body();
            if(basicResponse!=null){
                if(basicResponse.getMensaje()!=null) {
                    if(basicResponse.getMensaje().equals("Error token expiró")){
                        activity.tokenExpired();
                    } else if(basicResponse.getMensaje().equals("Error token inválido")){
                        activity.tokenInvalid();
                    } else if (basicResponse.getMensaje().equals("Clave cambiada con exito con éxito")) {
                        activity.passwordChangedOrCreatedSuccessfully(passwordToChange);
                    }
                }
            } else {
                Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
            }
        } else {
            String basicResponse = null;
            try {
                basicResponse = response.errorBody().string();

                Toast.makeText(context, basicResponse, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progress.dismissProgress();
    }

    @Override
    public void onFailure(Call<ChangeOrCreatePassResponse> call, Throwable t) {
        Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
        progress.dismissProgress();
    }
}


