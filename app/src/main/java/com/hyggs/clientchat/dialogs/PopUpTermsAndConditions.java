package com.hyggs.clientchat.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.hyggs.clientchat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopUpTermsAndConditions extends DialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.closeDialog)
    ImageView closeDialog;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.verifyMyAccountButton)
    Button verifyMyAccountButton;

    private Unbinder unbinder;

    private int currentNumberOfHours = 1;

    private VerifyMyAccountClicked mCallback;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.textTermsAndConditions)
    TextView textTermsAndConditions;

    private String termsAndConditions = "";

    public interface VerifyMyAccountClicked {
//        void verifyMyAccountAction();
        void goToSignature();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_terms_and_conditions, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);

        closeDialog.setOnClickListener(v-> dismiss());

        assert getArguments() != null;
        termsAndConditions = getArguments().getString("termsAndConditionsText");
        textTermsAndConditions.setText(termsAndConditions);

        verifyMyAccountButton.setOnClickListener(v->{
            dismiss();
            mCallback.goToSignature();
        });

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;

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
