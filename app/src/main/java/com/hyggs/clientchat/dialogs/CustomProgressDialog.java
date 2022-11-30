package com.hyggs.clientchat.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyggs.clientchat.R;

public class CustomProgressDialog extends ProgressDialog {

    //private AnimationDrawable animation;

    public TextView messageText;

    public CharSequence message;

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public static ProgressDialog ctor(Context context, int theme) {
        CustomProgressDialog dialog = new CustomProgressDialog(context, theme);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_progress_dialog);
        ImageView la = findViewById(R.id.animation);

        Glide.with(getContext()).asGif()
                .load(R.drawable.loading_hyggs)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(la);

        messageText = findViewById(R.id.progressText);

         if (message != null) {
            setCustomMessage("");
        }
    }

    public void setCustomMessage(CharSequence message) {
        if (message != null && messageText != null) {
            messageText.setText(message);
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);

        this.message = message;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
