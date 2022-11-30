package com.hyggs.clientchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.databinding.ActivityRegisterBinding;
import com.hyggs.clientchat.dialogs.PopUpMessages;
import com.hyggs.clientchat.dialogs.PopUpSignature;
import com.hyggs.clientchat.dialogs.PopUpTermsAndConditions;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.managerservices.ServiceGetTermsAndConditionsManager;
import com.hyggs.clientchat.managerservices.ServiceRegisterManager;
import com.hyggs.clientchat.managerservices.ServiceUploadFileManager;
import com.hyggs.clientchat.models.RegisterResponse;
import com.hyggs.clientchat.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements
        PopUpTermsAndConditions.VerifyMyAccountClicked,
        PopUpSignature.VerifyMyAccountClicked {

    private ActivityRegisterBinding binding;
    private AutoCompleteTextView codePhone;
    private TextView termsAndConditions;
    private Button verifyMyAccountButtonRegister;
    View.OnClickListener clickListener = null;
    private EditText phoneRegister;
    private EditText lastNameRegister;
    private EditText firstNameRegister;
    private EditText emailRegister;
    private EditText companyName;
    private String lastName = "";
    private String firstName = "";
    private String email = "";
    private String phone = "";
    private String company = "";
    private String code = "";

    private ServiceRegisterManager serviceRegisterManager;
    private ManagerSharedPreferences managerSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        serviceRegisterManager = new ServiceRegisterManager(this, this);
        managerSharedPreferences = new ManagerSharedPreferences(this);
        codePhone = binding.codePhoneRegister;
        verifyMyAccountButtonRegister = binding.verifyMyAccountButtonRegister;
        termsAndConditions = binding.termsAndConditions;
        companyName = binding.companyName;
        phoneRegister = binding.phoneRegister;
        lastNameRegister = binding.lastNameRegister;
        emailRegister = binding.emailRegister;
        firstNameRegister = binding.firstNameRegister;

//        emailRegister.setText("jviloriapineda@gmail.com");
//        codePhone.setText("+57");
//        phoneRegister.setText("3158078270");
//        lastNameRegister.setText("Supplier");
//        firstNameRegister.setText("Jesús");
//        companyName.setText("DATIA");

        ArrayList<String> listCountryCodes = new ArrayList<>();
        JSONArray mainObject = null;

        try {
            mainObject = new JSONArray(Utilities.getCountryCodes());
            int totalItems = mainObject.length();
            for(int i = 0; i<totalItems; i++){
                JSONObject country = mainObject.getJSONObject(i);
                String codeObtained = country.getString("dialCode");
                String countryObtained = country.getString("name");
                listCountryCodes.add(codeObtained+" - "+countryObtained);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, R.layout.item_test, listCountryCodes);

        codePhone.setThreshold(1);
        codePhone.setAdapter(adapter);
        codePhone.setDropDownWidth(-1);

        clickListener = view -> {
            if(view==codePhone){
                codePhone.setText("+");
                codePhone.showDropDown();
            } else if(view==termsAndConditions){

                //Upload signature
                lastName = lastNameRegister.getText().toString();
                firstName = firstNameRegister.getText().toString();
                email = emailRegister.getText().toString();
                phone = phoneRegister.getText().toString();
                company = companyName.getText().toString();
                code = codePhone.getText().toString();

                if(code.isEmpty())
                {
                    DialogFragment newFragment = new PopUpMessages();
                    Bundle args = new Bundle();
                    args.putString("messageToShow", "You must select your country code.");
                    newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "termsAndConditions");
                    return;
                }

                if(!lastName.isEmpty() &&
                        !firstName.isEmpty() &&
                        !email.isEmpty() &&
                        !company.isEmpty() &&
                        !phone.isEmpty() ){

                    ServiceGetTermsAndConditionsManager serviceGetTermsAndConditionsManager = new ServiceGetTermsAndConditionsManager(this, this);
                    serviceGetTermsAndConditionsManager.getTermsAndConditions();

                } else {
                    DialogFragment newFragment = new PopUpMessages();
                    Bundle args = new Bundle();
                    args.putString("messageToShow", "You must fill in all the fields.");
                    newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "termsAndConditions");
                }

            } else if(view==verifyMyAccountButtonRegister){

                //Upload signature
                lastName = lastNameRegister.getText().toString();
                firstName = firstNameRegister.getText().toString();
                email = emailRegister.getText().toString();
                phone = phoneRegister.getText().toString();
                code = codePhone.getText().toString();
                company = companyName.getText().toString();

                if(code.isEmpty())
                {
                    DialogFragment newFragment = new PopUpMessages();
                    Bundle args = new Bundle();
                    args.putString("messageToShow", "You must select your country code.");
                    newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "termsAndConditions");
                    return;
                }

                if(!lastName.isEmpty() &&
                        !firstName.isEmpty() &&
                        !email.isEmpty() &&
                        !phone.isEmpty()){

                    ServiceGetTermsAndConditionsManager serviceGetTermsAndConditionsManager = new ServiceGetTermsAndConditionsManager(this, this);
                    serviceGetTermsAndConditionsManager.getTermsAndConditions();

                } else {
                    DialogFragment newFragment = new PopUpMessages();
                    Bundle args = new Bundle();
                    args.putString("messageToShow", "You must fill in all the fields.");
                    newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "termsAndConditions");
                }



            }
        };

        codePhone.setOnItemClickListener((parent, view, position, id) -> {
            String countrySelected = listCountryCodes.get(position);
            String onlyCode = countrySelected.substring(0, countrySelected.lastIndexOf("-") + 1);
            onlyCode = onlyCode.replace(" -", "");
            onlyCode = onlyCode.trim();
            codePhone.setText(onlyCode);
        });

        codePhone.setOnClickListener(clickListener);
        termsAndConditions.setOnClickListener(clickListener);
        verifyMyAccountButtonRegister.setOnClickListener(clickListener);
    }

    public void termsAndConditionsFromService(String dataTermsAndConditions){
        DialogFragment newFragment = new PopUpTermsAndConditions();
        Bundle args = new Bundle();
        args.putBoolean("isFromMembership", false);
        args.putString("termsAndConditionsText", dataTermsAndConditions);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
    }

    @Override
    public void signatureCompleted(String path) {
        ServiceUploadFileManager serviceUploadFileManager = new ServiceUploadFileManager(this, this);
        serviceUploadFileManager.sendImage(path);
    }

    @Override
    public void goToSignature() {
        DialogFragment newFragment = new PopUpSignature();
        Bundle args = new Bundle();
        args.putBoolean("fromRegister", true);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "termsAndConditions");
    }

    public void clientRegisterSuccessfully(String s, RegisterResponse data) {
        saveDateUserLocally();

        String idUser = data.getDatos().getId();
        String phoneNumber = codePhone.getText().toString()+phoneRegister.getText().toString();

        managerSharedPreferences.setUserPhone(phoneNumber);
        managerSharedPreferences.setUserId(idUser);

        Intent intent = new Intent(this, CodeVerificationActivity.class);
        intent.putExtra("isRecoverPassword", false);
        intent.putExtra("tokenGenerated", data.getToken().getToken());
        startActivity(intent);
    }

    private void saveDateUserLocally() {

    }

    public void messageSentSuccessfully(String mensaje, String token) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void finishRegistration(String urlSignature) {
        phone = code+phone;
        serviceRegisterManager.registerUser(email,
                firstName,
                lastName,
                urlSignature,
                company,
                phone);
    }

    public void errorService(String message) {
        DialogFragment newFragment = new PopUpMessages();
        Bundle args = new Bundle();
        args.putString("messageToShow", message);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "errorMessageRegister");
    }

}