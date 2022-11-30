package com.hyggs.clientchat.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.activities.SlidersActivity;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PopUpExit extends DialogFragment {

    private Unbinder unbinder;
    private ManagerSharedPreferences managerSharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        managerSharedPreferences = new ManagerSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.dialog_exit,null);
        unbinder = ButterKnife.bind(this, view);

        builder.setView(view);

        Dialog dialog = builder.create();
        if(dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return dialog;
    }

    @OnClick(R.id.btn_cancel)
    public void cancel(View view) {
        this.getDialog().dismiss();
    }

    @OnClick(R.id.btn_accept)
    public void accept(View view) {

            managerSharedPreferences.setLogged(false);
            managerSharedPreferences.clearAll();

            Intent intent = new Intent(getContext(), SlidersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
