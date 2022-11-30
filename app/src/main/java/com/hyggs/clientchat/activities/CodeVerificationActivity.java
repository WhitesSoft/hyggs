package com.hyggs.clientchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.hyggs.clientchat.databinding.ActivityCodeVerificationBinding;
import com.hyggs.clientchat.dialogs.PopUpMessages;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.managerservices.ServiceGetTokenManager;
import com.hyggs.clientchat.models.Token;
import com.hyggs.clientchat.utilities.Utilities;

public class CodeVerificationActivity extends AppCompatActivity {

    private ActivityCodeVerificationBinding binding;
    private EditText numberOne, numberTwo, numberThree, numberFour, numberFive, numberSix;
    private TextView textViewPhone;
    private CodeVerificationActivity presentActivity;
    private Button registerButtonCodeVerification;
    private boolean isRecoverPassword = false;
    private ManagerSharedPreferences managerSharedPreferences;
    private String codeCompleted = "";
    private String tokenGenerated = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managerSharedPreferences = new ManagerSharedPreferences(this);
        registerButtonCodeVerification = binding.registerButtonCodeVerification;
        numberOne = binding.numberOne;
        numberTwo = binding.numberTwo;
        numberThree = binding.numberThree;
        numberFour = binding.numberFour;
        numberFive = binding.numberFive;
        numberSix = binding.numberSix;
        textViewPhone = binding.textViewPhone;

        if(getIntent().getExtras()!=null){
            isRecoverPassword = getIntent().getExtras().getBoolean("isRecoverPassword");
            tokenGenerated = getIntent().getExtras().getString("tokenGenerated");

            if(tokenGenerated.equals("generateANewOne")){
                ServiceGetTokenManager serviceGetTokenManager = new ServiceGetTokenManager(this, this);
                serviceGetTokenManager.getTokenUser(managerSharedPreferences.getUserEmail());
            }
        }

        String phoneSaved = managerSharedPreferences.getUserPhone();
        int totalLengthPhone = phoneSaved.length();
        String lastDigits = phoneSaved.substring(totalLengthPhone-4, totalLengthPhone);
        textViewPhone.setText(lastDigits);

        presentActivity = this;

        numberOne.requestFocus();

        TextWatcher numberOnTextWatcher;
        numberOnTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberOne.getText().toString().length()==1){
                    numberTwo.requestFocus();
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number one");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberOne.getText().toString().length()==1) {
                    numberTwo.requestFocus();
                }else if(numberOne.getText().toString().isEmpty()){
                    //Same focus
                    Log.i("CodeVerification", "Can't go back, is at one");
                    numberOne.requestFocus();
                }
            }
        };
        numberOne.addTextChangedListener(numberOnTextWatcher);
        numberOne.setOnClickListener(v-> numberOne.setSelectAllOnFocus(true));
        numberOne.setOnFocusChangeListener((view, b) -> numberOne.setSelectAllOnFocus(true));

        numberOne.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberOne.getText().toString().isEmpty()){
                    Log.i("CodeVerification", "Have to write again");
                }
            }
            return false;
        });

        TextWatcher numberTwoTextWatcher;
        numberTwoTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberTwo.getText().toString().length()==1){
                    numberThree.requestFocus();
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number two");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberTwo.getText().toString().length()==1) {
                    numberThree.requestFocus();
                }else if(numberTwo.getText().toString().isEmpty()){
                    //Same focus
                    numberOne.requestFocus();
                }
            }
        };
        numberTwo.addTextChangedListener(numberTwoTextWatcher);
        numberTwo.setOnClickListener(v-> numberTwo.setSelectAllOnFocus(true));
        numberTwo.setOnFocusChangeListener((view, b) -> numberTwo.setSelectAllOnFocus(true));
        numberTwo.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberTwo.getText().toString().isEmpty()){
                    numberOne.removeTextChangedListener(numberOnTextWatcher);
//                    numberOne.setText("");
                    numberOne.requestFocus();
                    numberOne.addTextChangedListener(numberOnTextWatcher);
                }
            }
            return false;
        });

        TextWatcher numberThreeTextWatcher;
        numberThreeTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberThree.getText().length()==1){
                    numberFour.requestFocus();
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number three");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberThree.getText().toString().length()==1) {
                    numberFour.requestFocus();
                }else if(numberThree.getText().toString().isEmpty()){
                    //Same focus
                    numberTwo.requestFocus();
                }
            }
        };
        numberThree.addTextChangedListener(numberThreeTextWatcher);
        numberThree.setOnClickListener(v-> numberThree.setSelectAllOnFocus(true));
        numberThree.setOnFocusChangeListener((view, b) -> numberThree.setSelectAllOnFocus(true));
        numberThree.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberThree.getText().toString().isEmpty()){
                    numberTwo.removeTextChangedListener(numberTwoTextWatcher);
//                    numberTwo.setText("");
                    numberTwo.requestFocus();
                    numberTwo.addTextChangedListener(numberTwoTextWatcher);
                }
            }
            return false;
        });


        TextWatcher numberFourTextWatcher;
        numberFourTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberFour.getText().length()==1){
                    numberFive.requestFocus();
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number four");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberFour.getText().toString().length()==1) {
                    numberFive.requestFocus();
                }else if(numberFour.getText().toString().isEmpty()){
                    //Same focus
                    numberThree.requestFocus();
                }
            }
        };
        numberFour.addTextChangedListener(numberFourTextWatcher);
        numberFour.setOnClickListener(v-> numberFour.setSelectAllOnFocus(true));
        numberFour.setOnFocusChangeListener((view, b) -> numberFour.setSelectAllOnFocus(true));
        numberFour.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberFour.getText().toString().isEmpty()){
                    numberThree.removeTextChangedListener(numberThreeTextWatcher);
//                    numberThree.setText("");
                    numberThree.requestFocus();
                    numberThree.addTextChangedListener(numberThreeTextWatcher);
                }
            }
            return false;
        });

        TextWatcher numberFiveTextWatcher;
        numberFiveTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberFive.getText().length()==1){
                    numberSix.requestFocus();
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number five");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberFive.getText().toString().length()==1) {
                    numberSix.requestFocus();
                }else if(numberFive.getText().toString().isEmpty()){
                    //Same focus
                    numberFour.requestFocus();
                }
            }
        };

        numberFive.addTextChangedListener(numberFiveTextWatcher);
        numberFive.setOnClickListener(v-> numberFive.setSelectAllOnFocus(true));
        numberFive.setOnFocusChangeListener((view, b) -> numberFive.setSelectAllOnFocus(true));
        numberFive.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberFive.getText().toString().isEmpty()){
                    numberFour.removeTextChangedListener(numberFourTextWatcher);
//                    numberFour.setText("");
                    numberFour.requestFocus();
                    numberFour.addTextChangedListener(numberFourTextWatcher);
                }
            }
            return false;
        });

        TextWatcher numberSixTextWatcher;
        numberSixTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(numberSix.getText().length()==1){
                    Log.i("CodeVerification", charSequence+" "+i+" "+i1+" "+i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("CodeVerification", "Modifying number six");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(numberSix.getText().toString().length()==1) {
                    Utilities.hideKeyboard(presentActivity);
                }else if(numberSix.getText().toString().isEmpty()){
                    numberFive.requestFocus();
                }
            }
        };

        numberSix.addTextChangedListener(numberSixTextWatcher);
        numberSix.setOnClickListener(v-> numberSix.setSelectAllOnFocus(true));
        numberSix.setOnFocusChangeListener((view, b) -> numberSix.setSelectAllOnFocus(true));
        numberSix.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                if(numberSix.getText().toString().isEmpty()){
                    numberFive.removeTextChangedListener(numberFiveTextWatcher);
                    numberFive.requestFocus();
                    numberFive.addTextChangedListener(numberFiveTextWatcher);
                }
            }
            return false;
        });

        registerButtonCodeVerification.setOnClickListener(v -> {
            String numberOneResult = numberOne.getText().toString();
            String numberTwoResult = numberTwo.getText().toString();
            String numberThreeResult = numberThree.getText().toString();
            String numberFourResult = numberFour.getText().toString();
            String numberFiveResult = numberFive.getText().toString();
            String numberSixResult = numberSix.getText().toString();

            if(numberOneResult.isEmpty() ||
                    numberTwoResult.isEmpty() ||
                    numberThreeResult.isEmpty() ||
                    numberFourResult.isEmpty() ||
                    numberFiveResult.isEmpty() ||
                    numberSixResult.isEmpty()){

                DialogFragment newFragment = new PopUpMessages();
                Bundle args = new Bundle();
                args.putString("messageToShow", "Verification code incorrect.");
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "termsAndConditions");

            } else {

                String tokenComplete = numberOneResult +
                        numberTwoResult +
                        numberThreeResult +
                        numberFourResult +
                        numberFiveResult +
                        numberSixResult;

                if(tokenComplete.equals(tokenGenerated)){
                    if (isRecoverPassword) {
                        //Go to create password
                        Intent intent = new Intent(this, PasswordCreationAndRecoveryActivity.class);
                        intent.putExtra("isCreation", false);
                        intent.putExtra("tokenToChangeOrCreatePassword", tokenGenerated);
                        startActivity(intent);
                    } else {
                        //Go to create password
                        Intent intent = new Intent(this, PasswordCreationAndRecoveryActivity.class);
                        intent.putExtra("isCreation", true);
                        intent.putExtra("tokenToChangeOrCreatePassword", tokenGenerated);
                        startActivity(intent);
                    }
                } else {
                    DialogFragment newFragment = new PopUpMessages();
                    Bundle args = new Bundle();
                    args.putString("messageToShow", "Verification code incorrect.");
                    newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "termsAndConditions");
                }
            }
        });
    }

    public void tokenObtainedSuccessfully(String mensaje, Token token, String phone) {
        Log.i("LoginActivity", mensaje);
        managerSharedPreferences.setUserPhone(phone);
        managerSharedPreferences.setUserId(token.getId_user());
        tokenGenerated = token.getToken();
    }
}