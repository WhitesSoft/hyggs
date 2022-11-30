package com.hyggs.clientchat.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopUpMessages extends DialogFragment {

    private Unbinder unbinder;
    private ManagerSharedPreferences managerSharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text)
    TextView textMessage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.closeDialog)
    ImageView closeDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        managerSharedPreferences = new ManagerSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.dialog_messages,null);
        unbinder = ButterKnife.bind(this, view);

        builder.setView(view);

        closeDialog.setOnClickListener(v-> dismiss());

        if(getArguments()!=null){
            if(getArguments().getString("messageToShow")!=null){
                String messageToShow = getArguments().getString("messageToShow");
                textMessage.setText(messageToShow);
            }
        }

        Dialog dialog = builder.create();
        if(dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
