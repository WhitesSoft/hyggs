package com.hyggs.clientchat.managerservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.CodeVerificationActivity;
import com.hyggs.clientchat.activities.LoginActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.models.GetTokenResponse;
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

public class ServiceGetTokenManager implements Callback<GetTokenResponse> {

    private Context context;
    private ManagerProgressDialog progress;
    private LoginActivity activity;
    private CodeVerificationActivity activityCodeVerification;

    public ServiceGetTokenManager(Context context, LoginActivity activity) {
        this.context = context;
        this.activity = activity;
        progress = new ManagerProgressDialog(context);
    }

    public ServiceGetTokenManager(Context context, CodeVerificationActivity activity) {
        this.context = context;
        this.activityCodeVerification = activity;
        progress = new ManagerProgressDialog(context);
    }

    public void getTokenUser(String email) {
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

            Call<GetTokenResponse> call;
            call = retrofitIR.getTokenEmail(Provider.KEY,
                    Provider.idProject,
                    email);

            call.enqueue(this);
        } else {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
            Log.i( "Conexion", "1");
        }
    }

    @Override
    public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

        int code = response.code();
        if (code == 200) {
            GetTokenResponse basicResponse = response.body();
            if(basicResponse!=null){
                if(basicResponse.getMensaje()!=null) {
                    if(basicResponse.getMensaje().equals("Registro Token con éxito")){
                        if(activity!=null){
                            activity.tokenObtainedSuccessfully(basicResponse.getMensaje(), basicResponse.getToken(), basicResponse.getPhone());

                        }else if(activityCodeVerification!=null){
                            activityCodeVerification.tokenObtainedSuccessfully(basicResponse.getMensaje(), basicResponse.getToken(), basicResponse.getPhone());

                        }
                    }
                }
            } else {
                Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
            }
        } else {
            String basicResponse;
            try {
                basicResponse = response.errorBody().string();

                JSONObject errorJson = new JSONObject(basicResponse);
                String messageError = errorJson.getString("mensaje");
                activity.errorService(messageError);

//                Toast.makeText(context, basicResponse, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        progress.dismissProgress();
    }

    @Override
    public void onFailure(Call<GetTokenResponse> call, Throwable t) {
        Toast.makeText(context, "Register service error.", Toast.LENGTH_LONG).show();
        progress.dismissProgress();
    }
}


