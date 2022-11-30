package com.hyggs.clientchat.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.hyggs.clientchat.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopUpSignature extends DialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.closeDialog)
    ImageView closeDialog;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnClearSignature)
    ImageView btnClearSignature;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnAcceptSignature)
    Button btnAcceptSignature;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signatureView)
    SignaturePad signatureView;

    private Unbinder unbinder;
    private VerifyMyAccountClicked mCallback;

    private Bitmap bitmap;
    String path;
    private static final String Image_DIRECTORY="/signatureHyggs";

    public interface VerifyMyAccountClicked {
        void signatureCompleted(String path);
    }

    private boolean fromRegister = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_signature, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);

        if(getArguments()!=null){
            if(getArguments().getBoolean("fromRegister")){
                fromRegister = getArguments().getBoolean("fromRegister");
            }else{
                fromRegister = false;
            }
        }

        closeDialog.setOnClickListener(v-> dismiss());

        btnAcceptSignature.setOnClickListener(v->{
            bitmap=signatureView.getSignatureBitmap();
            path = saveImage(bitmap);
            if (path.isEmpty()){
                Toast.makeText(getContext(), "Signature couldn't be saved.", Toast.LENGTH_LONG).show();
            }else{
                dismiss();
//                if (fromRegister){
                    mCallback.signatureCompleted(path);
//                }else{
//                    mCallback.verifyMyAccountAction();
//                }
            }
        });

        btnClearSignature.setOnClickListener(v->{
            signatureView.clear();
        });

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;

    }

    private String saveImage(Bitmap myBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String urlFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + Image_DIRECTORY;
        File wallpaperDirectory = new File(urlFolder);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            String filePath = urlFolder + "/signature" +Calendar.getInstance().getTimeInMillis() + ".jpg";
            Log.d("File Path", filePath);
                try {
                    File f = new File(wallpaperDirectory, "signature"+Calendar.getInstance().getTimeInMillis() + ".jpg");
                    if (f.exists())
                        f.delete();
                    f.createNewFile();
                    FileOutputStream out = new FileOutputStream(f);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    myBitmap.recycle();
                    return f.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File f = new File(wallpaperDirectory, "signature"+Calendar.getInstance().getTimeInMillis() + ".jpg");
                if (f.exists())
                    f.delete();
                try {
                    f.createNewFile();
                    FileOutputStream out = new FileOutputStream(f);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    myBitmap.recycle();
                    return f.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (VerifyMyAccountClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
