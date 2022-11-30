package com.hyggs.clientchat.managerservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.widget.Toast;

import com.hyggs.clientchat.Provider;
import com.hyggs.clientchat.activities.RegisterActivity;
import com.hyggs.clientchat.interfaces.RetrofitInterface;
import com.hyggs.clientchat.managers.ManagerProgressDialog;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.models.UploadFileResponse;
import com.hyggs.clientchat.utilities.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUploadFileManager {

    private Context context;
    private ManagerProgressDialog progress;
    private ManagerSharedPreferences manageSharedPreferences;
    private RegisterActivity registerActivity;

    public ServiceUploadFileManager(Context context, RegisterActivity activity) {
        this.context = context;
        progress = new ManagerProgressDialog(context);
        registerActivity = activity;
        manageSharedPreferences = new ManagerSharedPreferences(context);
    }

    public void sendImage(String fileUri) {
        if (Utilities.isConnected(context)) {
            progress.showProgress();
            resizeImage(fileUri);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(180, TimeUnit.SECONDS).connectTimeout(180, TimeUnit.SECONDS).addInterceptor(logging).build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Provider.urlMain).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
            RetrofitInterface retrofitIR = retrofit.create(RetrofitInterface.class);

            resizeImage(fileUri);
            File file = new File(fileUri);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file_up", file.getName(), requestFile);
            RequestBody key = RequestBody.create(MediaType.parse("multipart/form-data"), Provider.KEY);
            //Notify Nestor that upload files service, can't send it with-out id-user
            RequestBody idUser = RequestBody.create(MediaType.parse("multipart/form-data"), "125");

            Call<UploadFileResponse> call = retrofitIR.uploadFile(key, idUser, body);

            call.enqueue(new Callback<UploadFileResponse>() {
                @Override
                public void onResponse(Call<UploadFileResponse> call, Response<UploadFileResponse> response) {
                    int code = response.code();
                    if (code == 200) {
                        UploadFileResponse basicResponse = response.body();
                        System.out.println(basicResponse.getUrlimage());

                        if (basicResponse.getStatus().equals("200")) {
                            if(registerActivity!=null){
                                registerActivity.finishRegistration(basicResponse.getUrlimage());
                            }else{
                                showDialogMessage("Membership signature.", basicResponse.getStatus()+" - "+basicResponse.getUrlimage());
                            }
                        }else{
                            try {
                                showDialogMessage("Service error.", basicResponse.getStatus()+" - "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        if (response.errorBody() != null) {
                            showDialogMessage("Service error.", ""+response.errorBody());
                        }else{
                            showDialogMessage("Service error.", ""+response.message());
                        }
                    }
                    progress.dismissProgress();
                }

                @Override
                public void onFailure(Call<UploadFileResponse> call, Throwable t) {
                    progress.dismissProgress();
                    showDialogMessage("Error en el servicio", call.toString()+" - "+t.getLocalizedMessage()+" - "+t.getMessage());

                }
            });
        } else {
            Log.i( "Conexion", "4");
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    private void showDialogMessage(String tituloVentana, String textoMensaje){
        if(context != null || !((Activity) context).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setNeutralButton("Aceptar", (dialog, which) -> {
                //No hacer nada
            });
            builder.setTitle(tituloVentana);
            builder.setMessage(textoMensaje);
            AlertDialog currentDialog = builder.create();
            currentDialog.show();
        }

    }

    public void resizeImage(String pathImage) {
        try {
            int inWidth;
            int inHeight;
            int dstWidth = 600;
            int dstHeight = 300;
            InputStream in = new FileInputStream(pathImage);
            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;
            // decode full image pre-resized
            in = new FileInputStream(pathImage);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / dstWidth, inHeight / dstHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);
            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);
            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
            // save image
            try {
                FileOutputStream out = new FileOutputStream(pathImage);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (Exception e) {
                Log.e("Image", e.getMessage(), e);
            }
        } catch (IOException e) {
            Log.e("Image", e.getMessage(), e);
        }
    }

}