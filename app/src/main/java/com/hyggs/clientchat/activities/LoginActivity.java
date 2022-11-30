package com.hyggs.clientchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.hyggs.clientchat.databinding.ActivityLoginBinding;
import com.hyggs.clientchat.dialogs.PopUpMessages;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.managerservices.ServiceGetTokenManager;
import com.hyggs.clientchat.managerservices.ServiceLoginManager;
import com.hyggs.clientchat.models.LoginResponse;
import com.hyggs.clientchat.models.Token;
import com.hyggs.clientchat.models.UserData;
import com.hyggs.clientchat.utilities.Utilities;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextView forgotPassword;
    private Button loginButton;
    private ServiceLoginManager loginManager;
    private EditText passwordEditText, userEditText;
    private ManagerSharedPreferences managerSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managerSharedPreferences = new ManagerSharedPreferences(this);
        loginManager = new ServiceLoginManager(this, this);
        forgotPassword = binding.forgotPassword;
        loginButton = binding.loginButton;
        passwordEditText = binding.passwordEditText;
        userEditText = binding.userEditText;

        //Comment before uploading
        userEditText.setText("client");
        passwordEditText.setText("I26255^E^s#Q");

        forgotPassword.setOnClickListener(v->{
            //Go to verification window
            String emailData = userEditText.getText().toString();
            if(emailData.isEmpty()){
                //Can't send token because there is no email
                DialogFragment newFragment = new PopUpMessages();
                Bundle args = new Bundle();
                args.putString("messageToShow", "Email is empty, we can't send the token.");
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "messageEmailEmpty");
            } else {
                managerSharedPreferences.setUserEmail(emailData);
                ServiceGetTokenManager serviceGetTokenManager = new ServiceGetTokenManager(this, this);
                serviceGetTokenManager.getTokenUser(emailData);
            }
        });

        loginButton.setOnClickListener(v->{
            //Go to welcome activity
            String userText = userEditText.getText().toString();
            String passwordText = passwordEditText.getText().toString();

            if(!userText.isEmpty() && !passwordText.isEmpty()){
                managerSharedPreferences.setUserEmail(userText);
                managerSharedPreferences.setUserPassword(passwordText);
                loginManager.loginUser(userText, Utilities.encodeSHA256(passwordText));

            } else {
                Toast.makeText(this, "Login in test mode.", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, HomeChatActivity.class));

//                Intent intent = new Intent(this, DashboardActivity.class);
//                startActivity(intent);
            }
        });
    }

    public void tokenObtainedSuccessfully(String message, Token token, String phone) {
        Log.i("LoginActivity", message);
        managerSharedPreferences.setUserPhone(phone);
        managerSharedPreferences.setUserId(token.getId_user());

        Intent intent = new Intent(this, CodeVerificationActivity.class);
        intent.putExtra("isRecoverPassword", true);
        intent.putExtra("tokenGenerated", token.getToken());
        startActivity(intent);
    }

    public void clientDoesntExist(String message) {
        DialogFragment newFragment = new PopUpMessages();
        Bundle args = new Bundle();
        args.putString("messageToShow", message);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
    }

    public void makeLogin(LoginResponse data) {
        managerSharedPreferences.setUserPhone(data.getDatos().getTelefono());
        managerSharedPreferences.setUserId(data.getDatos().getId());
        managerSharedPreferences.setUserEmail(data.getDatos().getEmail());

//        if(data.getAdd_data().getEnable_client_membership_landing().equals("false")){
//            String idStatusCliente = data.getAdd_data().getId_status_client();
//            String idStatus = data.getDatos().getId_status();
//
        startActivity(new Intent(this, HomeChatActivity.class));

//            if(idStatusCliente.equals("1")){
//                if(idStatus.equals("1")){
//                    //GO TO DASHBOARD
//                    Intent intent = new Intent(this, DashboardActivity.class);
//                    startActivity(intent);
//                    this.finish();
//                } else {
//                    //STAY ON WELCOME
//                    Intent intent = new Intent(this, WelcomeActivity.class);
//                    startActivity(intent);
//                    this.finish();
//                }
//            }else{
//                //STAY ON WELCOME
//                Intent intent = new Intent(this, WelcomeActivity.class);
//                startActivity(intent);
//                this.finish();
//            }
//        }else if(data.getAdd_data().getEnable_client_membership_landing().equals("true")){
//            Intent intent = new Intent(this, MembershipActivity.class);
//            startActivity(intent);
//            finish();
////            Toast.makeText(this, "Enable client membership landing true.", Toast.LENGTH_LONG).show();
//        }
    }

    public void errorService(String messageError) {
        if(messageError.equals("Email no registrado")){
            messageError = "Email not registered.";
        }
        DialogFragment newFragment = new PopUpMessages();
        Bundle args = new Bundle();
        args.putString("messageToShow", messageError);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
    }
}