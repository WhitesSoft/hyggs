package com.hyggs.clientchat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.databinding.ActivityProfileBinding;
import com.hyggs.clientchat.managers.ManagerSharedPreferences;
import com.hyggs.clientchat.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuProfileFragment extends Fragment {

    private ManagerSharedPreferences managerSharedPreferences;
    private AutoCompleteTextView defaultTimeZone, typeDocumentDropDown, membershipDropDown, titleDropDown;
    private AutoCompleteTextView codePhone, dayBirth, monthBirth, yearBirth;

    static final String[] yearsForBirth = new String[] {" 1960"," 1961",
            " 1962"," 1963"," 1964"," 1965"," 1966"," 1967"," 1968"," 1969"," 1970"," 1971",
            " 1972"," 1973"," 1974"," 1975"," 1976"," 1977"," 1978"," 1979"," 1980","2081",
            " 1982"," 1983"," 1984"," 1985"," 1986"," 1987"," 1988"," 1989"," 1990"," 1991",
            " 1992"," 1993"," 1994"," 1995"," 1996"," 1997"," 1998"," 1999"," 2000"," 2001",};

    static final String[] listOptions = new String[] {" Option 1"," Option 2",
            " Option 3"," Option 4"," Option 5"," Option 6"," Option 7"};

    View.OnClickListener clickListener = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewer= inflater.inflate(R.layout.fragment_menu_profile, container, false);
        managerSharedPreferences = new ManagerSharedPreferences(requireContext());
        codePhone = viewer.findViewById(R.id.codePhoneRegister);
        dayBirth = viewer.findViewById(R.id.dayBirthRegister);
        monthBirth = viewer.findViewById(R.id.monthBirthRegister);
        yearBirth = viewer.findViewById(R.id.yearBirthRegister);
        defaultTimeZone = viewer.findViewById(R.id.defaultTimeZone);
        typeDocumentDropDown = viewer.findViewById(R.id.typeDocumentDropDown);
        membershipDropDown = viewer.findViewById(R.id.membershipDropDown);
        titleDropDown = viewer.findViewById(R.id.titleDropDown);
        codePhone = viewer.findViewById(R.id.codePhoneRegister);
        dayBirth = viewer.findViewById(R.id.dayBirthRegister);
        monthBirth = viewer.findViewById(R.id.monthBirthRegister);
        yearBirth = viewer.findViewById(R.id.yearBirthRegister);

        ArrayList<String> listCountryCodes = new ArrayList<>();
        JSONArray mainObject = null;

        try {
            mainObject = new JSONArray(Utilities.getCountryCodes());
            int totalItems = mainObject.length();
            for(int i = 0; i<totalItems; i++){
                JSONObject country = (JSONObject) mainObject.getJSONObject(i);
                String codeObtained = country.getString("dialCode");
                String countryObtained = country.getString("name");
                listCountryCodes.add(codeObtained+" - "+countryObtained);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (requireContext(), R.layout.item_test, listCountryCodes);

        codePhone.setThreshold(1);
        codePhone.setAdapter(adapter);
        codePhone.setDropDownWidth(-1);

        clickListener = view -> {
            if(view==codePhone){

                codePhone.setText("+");
//                Toast.makeText(this, "Click codes.", Toast.LENGTH_LONG).show();
                codePhone.showDropDown();

            } else if(view==dayBirth){

                dayBirth.setText(" ");
//                Toast.makeText(this, "Click day.", Toast.LENGTH_LONG).show();
                dayBirth.showDropDown();

            } else if(view==monthBirth){

                monthBirth.setText(" ");
//                Toast.makeText(this, "Click month.", Toast.LENGTH_LONG).show();
                monthBirth.showDropDown();

            } else if(view==yearBirth){

                yearBirth.setText(" ");
//                Toast.makeText(this, "Click year.", Toast.LENGTH_LONG).show();
                yearBirth.showDropDown();

            } else if(view==defaultTimeZone){

                defaultTimeZone.setText(" ");
                defaultTimeZone.showDropDown();

            } else if(view==typeDocumentDropDown){

                typeDocumentDropDown.setText(" ");
                typeDocumentDropDown.showDropDown();

            } else if(view==membershipDropDown){

                membershipDropDown.setText(" ");
                membershipDropDown.showDropDown();

            } else if(view==titleDropDown){

                titleDropDown.setText(" ");
                titleDropDown.showDropDown();
            }
        };

        codePhone.setOnItemClickListener((parent, view, position, id) -> {
            String countrySelected = listCountryCodes.get(position);
            String onlyCode = countrySelected.substring(0, countrySelected.lastIndexOf("-") + 1);
            onlyCode = onlyCode.replace(" -", "");
            onlyCode = onlyCode.trim();
            codePhone.setText(onlyCode);
        });

        ArrayList<String> daysBirth = new ArrayList<>();
        daysBirth.add(" 1");
        daysBirth.add(" 2");
        daysBirth.add(" 3");
        daysBirth.add(" 4");
        daysBirth.add(" 5");
        daysBirth.add(" 6");
        daysBirth.add(" 7");
        daysBirth.add(" 8");
        daysBirth.add(" 9");
        daysBirth.add(" 10");
        daysBirth.add(" 11");
        daysBirth.add(" 12");
        daysBirth.add(" 13");
        daysBirth.add(" 14");
        daysBirth.add(" 15");
        daysBirth.add(" 16");
        daysBirth.add(" 17");
        daysBirth.add(" 18");
        daysBirth.add(" 19");
        daysBirth.add(" 20");
        daysBirth.add(" 21");
        daysBirth.add(" 22");
        daysBirth.add(" 23");
        daysBirth.add(" 24");
        daysBirth.add(" 25");
        daysBirth.add(" 26");
        daysBirth.add(" 27");
        daysBirth.add(" 28");
        daysBirth.add(" 29");
        daysBirth.add(" 30");
        daysBirth.add(" 31");

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>
                (requireContext(), R.layout.item_test, daysBirth);

        dayBirth.setThreshold(1);
        dayBirth.setAdapter(adapterDays);

        ArrayList<String> monthBirthList = new ArrayList<>();
        monthBirthList.add(" January");
        monthBirthList.add(" February");
        monthBirthList.add(" March");
        monthBirthList.add(" April");
        monthBirthList.add(" May");
        monthBirthList.add(" June");
        monthBirthList.add(" July");
        monthBirthList.add(" August");
        monthBirthList.add(" September");
        monthBirthList.add(" November");
        monthBirthList.add(" October");
        monthBirthList.add(" December");

        ArrayAdapter<String> adapterMonths = new ArrayAdapter<>(requireContext(), R.layout.item_test, monthBirthList);

        monthBirth.setThreshold(1);
        monthBirth.setAdapter(adapterMonths);

        ArrayAdapter<String> adapterYears = new ArrayAdapter<>
                (requireContext(), R.layout.item_test, yearsForBirth);

        yearBirth.setThreshold(1);
        yearBirth.setAdapter(adapterYears);

        codePhone.setOnClickListener(clickListener);
        dayBirth.setOnClickListener(clickListener);
        monthBirth.setOnClickListener(clickListener);
        yearBirth.setOnClickListener(clickListener);



        ArrayAdapter<String> adapterOptionsLists = new ArrayAdapter<>
                (requireContext(), R.layout.item_test, listOptions);

        defaultTimeZone.setThreshold(1);
        defaultTimeZone.setAdapter(adapterOptionsLists);

        typeDocumentDropDown.setThreshold(1);
        typeDocumentDropDown.setAdapter(adapterOptionsLists);

        membershipDropDown.setThreshold(1);
        membershipDropDown.setAdapter(adapterOptionsLists);

        titleDropDown.setThreshold(1);
        titleDropDown.setAdapter(adapterOptionsLists);

        defaultTimeZone.setOnClickListener(clickListener);
        typeDocumentDropDown.setOnClickListener(clickListener);
        membershipDropDown.setOnClickListener(clickListener);
        titleDropDown.setOnClickListener(clickListener);

        return viewer;
    }
}