package com.hyggs.clientchat.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.models.Chating;
import com.hyggs.clientchat.models.Language;

import java.util.ArrayList;

public class AdapterLanguage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ArrayList<Language> listLanguage;
    private Context context;
    private LayoutInflater inflater;

    public AdapterLanguage(ArrayList<Language> languages, Context mContext){
        this.listLanguage = languages;
        this.context = mContext;
        inflater= LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_language,parent,false);
        LanguageViewHolder languageViewHolder = new LanguageViewHolder(view);
        return languageViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LanguageViewHolder languageViewHolder = (LanguageViewHolder) holder;
        Language languages = listLanguage.get(position);
        languageViewHolder.txtcountry.setText(languages.getTitle());
        languageViewHolder.imgcountry.setImageResource(languages.getImgcountry());
        if (languages.getState()!=0){

        }
    }

    @Override
    public int getItemCount() {
        return listLanguage.size();
    }

     class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  TextView txtcountry;
        private  ImageView imgcountry;

        public LanguageViewHolder(View itemView) {
            super(itemView);
            txtcountry = itemView.findViewById(R.id.txtcountry);
            imgcountry=itemView.findViewById(R.id.imgcountry);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(context, "Click: "+listLanguage.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

}
