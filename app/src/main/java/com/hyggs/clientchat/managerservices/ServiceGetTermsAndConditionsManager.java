package com.hyggs.clientchat.managerservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.RegisterActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.models.GetTermsAndConditionsResponse;
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

public class ServiceGetTermsAndConditionsManager implements Callback<GetTermsAndConditionsResponse> {

    private Context context;
    private ManagerProgressDialog progress;
    private RegisterActivity activityRegister;
    private String phoneNumberUser;

    public ServiceGetTermsAndConditionsManager(Context context, RegisterActivity activityRegister) {
        this.context = context;
        this.activityRegister = activityRegister;
        progress = new ManagerProgressDialog(context);
    }

    public void getTermsAndConditions() {
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

            Call<GetTermsAndConditionsResponse> call;
            call = retrofitIR.getTermsAndConditions(Provider.KEY);

            call.enqueue(this);
        } else {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
            Log.i( "Conexion", "1");
        }
    }

    @Override
    public void onResponse(Call<GetTermsAndConditionsResponse> call, Response<GetTermsAndConditionsResponse> response) {

        int code = response.code();
        if (code == 200) {
            GetTermsAndConditionsResponse basicResponse = response.body();
            if(basicResponse!=null){
                if(basicResponse.getStatus().equals("200")) {
                    if(activityRegister!=null) {
                        activityRegister.termsAndConditionsFromService(basicResponse.getData());
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
    public void onFailure(Call<GetTermsAndConditionsResponse> call, Throwable t) {
        Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
        progress.dismissProgress();
    }
}


