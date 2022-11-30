package com.hyggs.clientchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.hyggs.clientchat.databinding.ActivityPasswordCreationAndRecoveryBinding;
import com.hyggs.clientchat.dialogs.PopUpMessages;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.managerservices.ServiceCreateOrChangePasswordManager;
import com.hyggs.clientchat.utilities.Utilities;

public class PasswordCreationAndRecoveryActivity extends AppCompatActivity {

    private boolean isCreation = false;
    private EditText passwordEditText, passwordEditTextConfirm;
    private boolean isWatchingPassword = false;
    private boolean isWatchingPasswordConfirm = false;
    private String tokenReceived = "";
    private ManagerSharedPreferences managerSharedPreferences;

    private boolean isTokenExpired = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPasswordCreationAndRecoveryBinding binding = ActivityPasswordCreationAndRecoveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managerSharedPreferences = new ManagerSharedPreferences(this);

        tokenReceived = getIntent().getExtras().getString("tokenToChangeOrCreatePassword");

        TextView createOrRecoverPasswordTitle = binding.createOrRecoverPasswordTitle;
        Button createOrRecoverPasswordButton = binding.createOrRecoverPasswordButton;
        passwordEditText = binding.passwordEditText;
        ImageView icWatchPassword = binding.icWatchPassword;
        passwordEditTextConfirm = binding.passwordEditTextConfirm;
        ImageView icWatchPasswordConfirm = binding.icWatchPasswordConfirm;

        if(getIntent().getExtras()!=null){
            isCreation = getIntent().getExtras().getBoolean("isCreation");
        }else{
//            Toast.makeText(this, "No recibió nada", Toast.LENGTH_LONG).show();
        }

        icWatchPassword.setOnClickListener(v->{
            if(isWatchingPassword){
                isWatchingPassword = false;
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordEditText.setSelection(passwordEditText.getText().length());
            } else{
                isWatchingPassword = true;
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passwordEditText.setSelection(passwordEditText.getText().length());

            }
        });

        icWatchPasswordConfirm.setOnClickListener(v->{
            if(isWatchingPasswordConfirm){
                passwordEditTextConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isWatchingPasswordConfirm = false;
                passwordEditTextConfirm.setSelection(passwordEditTextConfirm.getText().length());

            } else{
                passwordEditTextConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isWatchingPasswordConfirm = true;
                passwordEditTextConfirm.setSelection(passwordEditTextConfirm.getText().length());

            }
        });

        if(isCreation){
            createOrRecoverPasswordButton.setText("Complete Register");
            createOrRecoverPasswordTitle.setText("Create your password");
        }else{
            createOrRecoverPasswordButton.setText("Reset Password");
            createOrRecoverPasswordTitle.setText("Password Recovery");
        }

        createOrRecoverPasswordButton.setOnClickListener(v->{
            //Go to welcome activity
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordEditTextConfirm.getText().toString();

            if(password.equals(passwordConfirm)) {
                ServiceCreateOrChangePasswordManager serviceCreateOrChangePasswordManager = new ServiceCreateOrChangePasswordManager(this, this);
                serviceCreateOrChangePasswordManager.changeOrCreatePassword(Utilities.encodeSHA256(password), managerSharedPreferences.getUserId(), tokenReceived);
            } else {
                DialogFragment newFragment = new PopUpMessages();
                Bundle args = new Bundle();
                args.putString("messageToShow", "Passwords don't match.");
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "termsAndConditions");
            }
        });

    }

    public void passwordChangedOrCreatedSuccessfully(String passwordToChange){
        managerSharedPreferences.setUserPassword(passwordToChange);
//        Intent intent = new Intent(this, DashboardActivity.class);
//        startActivity(intent);
//        this.finish();
        startActivity(new Intent(this, HomeChatActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTokenExpired){
            Intent intent = new Intent(this, CodeVerificationActivity.class);
            intent.putExtra("isRecoverPassword", !isCreation);
            intent.putExtra("tokenGenerated", "generateANewOne");
            startActivity(intent);
        }
    }

    public void tokenExpired() {
        //Get new token
        DialogFragment newFragment = new PopUpMessages();
        Bundle args = new Bundle();
        args.putString("messageToShow", "Last token expired, we've sent you a new one.");
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
        isTokenExpired = true;
    }

    public void tokenInvalid() {
        //Get new token
        DialogFragment newFragment = new PopUpMessages();
        Bundle args = new Bundle();
        args.putString("messageToShow", "Invalid verification code.");
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
    }

}