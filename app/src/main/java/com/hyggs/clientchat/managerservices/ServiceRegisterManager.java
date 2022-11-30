package com.hyggs.clientchat.managerservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.RegisterActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.models.RegisterResponse;
import com.hyggs.clientchat.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRegisterManager implements Callback<RegisterResponse> {

    private Context context;
    private ManagerProgressDialog progress;
    private RegisterActivity activity;

    public ServiceRegisterManager(Context context, RegisterActivity activity) {
        this.context = context;
        this.activity = activity;
        progress = new ManagerProgressDialog(context);
    }

    public void registerUser(String email,
                          String firstName,
                          String lastName,
                          String urlSignature,
                          String companyName,
                          String phone) {
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

            Call<RegisterResponse> call;
            call = retrofitIR.registerClient(Provider.KEY,
                    email,
                    "1",
                    "0.0.0.0",
                    Provider.idProject,
                    firstName,
                    //companyName,
                    lastName,
                    urlSignature, phone);

            call.enqueue(this);
        } else {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
            Log.i( "Conexion", "1");
        }
    }

    @Override
    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
        int code = response.code();
        if (code == 200) {
            RegisterResponse basicResponse = response.body();
            if(basicResponse!=null){
                if(basicResponse.getMensaje()!=null) {
                    if(basicResponse.getMensaje().equals("Registro Supplier con éxito")){
                        activity.clientRegisterSuccessfully("Registered.", basicResponse);
                    }else{
                        Toast.makeText(activity, "Another operation", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
            }
        } else {
            String basicResponse = null;
            try {
                basicResponse = response.errorBody().string();

                JSONObject errorJson = new JSONObject(basicResponse);
                String messageError = errorJson.getString("mensaje");
                activity.errorService(messageError);
                //Toast.makeText(context, basicResponse, Toast.LENGTH_LONG).show();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        progress.dismissProgress();
    }

    @Override
    public void onFailure(Call<RegisterResponse> call, Throwable t) {
        Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
        progress.dismissProgress();
    }
}


