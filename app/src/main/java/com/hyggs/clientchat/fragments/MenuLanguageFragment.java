package com.hyggs.clientchat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.adapters.AdapterChating;
import com.hyggs.clientchat.adapters.AdapterLanguage;
import com.hyggs.clientchat.models.Chating;
import com.hyggs.clientchat.models.Language;

import java.util.ArrayList;

public class MenuLanguageFragment extends Fragment {

    private AdapterLanguage adapterLanguage;
    ArrayList<Language> listLanguage = new ArrayList<>();
    private RecyclerView rvLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_menu_language, container, false);
        adapterLanguage = new AdapterLanguage(listLanguage,requireContext());
        rvLanguage=view.findViewById(R.id.rvlanguage);
        listLanguage.add(new Language(1, "English", R.drawable.single_flag_us));
        listLanguage.add(new Language(0, "Spanish", R.drawable.single_flag_es));
        listLanguage.add(new Language(0, "Russian", R.drawable.single_flag_ru));
        listLanguage.add(new Language(0, "French", R.drawable.single_flag_fr));
        listLanguage.add(new Language(0, "Korean", R.drawable.single_flag_co));
        listLanguage.add(new Language(0, "Mandarin", R.drawable.single_flag_man));
        listLanguage.add(new Language(0, "Italian", R.drawable.single_flag_it));
        listLanguage.add(new Language(0, "German", R.drawable.single_flag_ger));
        listLanguage.add(new Language(0, "Portuguese", R.drawable.single_flag_por));
        listLanguage.add(new Language(0, "Dutch", R.drawable.single_flag_du));
        listLanguage.add(new Language(0, "Japanese", R.drawable.single_flag_jap));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rvLanguage.setLayoutManager(linearLayoutManager);
        rvLanguage.setAdapter(adapterLanguage);
        return view;
    }
}