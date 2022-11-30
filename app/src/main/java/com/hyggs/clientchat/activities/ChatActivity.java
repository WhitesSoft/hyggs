package com.hyggs.clientchat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.adapters.AdapterChating;
import com.hyggs.clientchat.adapters.AdapterChatsRecyclerView;
import com.hyggs.clientchat.models.Chating;
import com.hyggs.clientchat.models.Chats;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    EditText textChat;
    RecyclerView rvchat;
    ImageView sendmsg,sendphoto,sendvoice,backbtn;
    private AdapterChating adapterChating;
    ArrayList<Chating> listChating = new ArrayList<>();
    String typechat=null;
    int controlcode=0;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle data = this.getIntent().getExtras();
        if(data !=null){
            typechat = data.getString("typechat");
        }
        adapterChating = new AdapterChating(listChating, R.layout.item_chat, this);
        rvchat = findViewById(R.id.rvchat);
        backbtn = findViewById(R.id.icBackImageView);
        textChat = findViewById(R.id.edittextchat);
        sendmsg = findViewById(R.id.sendmsg);
        sendphoto = findViewById(R.id.goToCameraImageView);
        sendvoice = findViewById(R.id.recordAudioImageView);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0){
                    sendmsg.setVisibility(View.VISIBLE);
                    sendphoto.setVisibility(View.GONE);
                    sendvoice.setVisibility(View.GONE);
                }else{
                    sendmsg.setVisibility(View.GONE);
                    sendphoto.setVisibility(View.VISIBLE);
                    sendvoice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0){
                    sendmsg.setVisibility(View.VISIBLE);
                    sendphoto.setVisibility(View.GONE);
                    sendvoice.setVisibility(View.GONE);
                }else{
                    sendmsg.setVisibility(View.GONE);
                    sendphoto.setVisibility(View.VISIBLE);
                    sendvoice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (typechat!=null){
            if (typechat.equals("bot")){
                listChating.add(new Chating("Hi Timo, what do you need today?",
                        "️1️⃣ New Request. \n2️⃣ Existing service info.\n3️⃣ I only need info.",
                        0,
                        ""));
            }
        }

        sendmsg.setOnClickListener(v ->{
            String textsend = textChat.getText().toString();
            listChating.add(new Chating(textsend,
                    "",
                    1,
                    ""));
            rvchat.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (controlcode==0){
                        if (textsend.equals("1")){
                            listChating.add(new Chating("What information about the service Limousine paris do you want to obtain?",
                                    "️1️⃣ Textual Requirement\n" +
                                            "2️⃣ General\n" +
                                            "3️⃣ Preferences\n" +
                                            "4️⃣ Custom Fields\n" +
                                            "5️⃣ Itinerary\n" +
                                            "6️⃣ Guests\n" +
                                            "7️⃣ Attached files\n" +
                                            "8️⃣ Legal Documents\n",
                                    0,
                                    ""));
                            controlcode =1;
                        }

                    }else{
                        switch (textsend){
                            case "1":
                                listChating.add(new Chating("Textual requirement from the Limousine paris service",
                                        "️1-I want a black limousine for 8 passengers, with 3 bottles of moet and cheese, available for 2 days starting the 12/10/22 at 8 am in the loft hotel in london.",
                                        0,
                                        ""));
                                break;
                            case "2":
                                listChating.add(new Chating("General Information from the Limousine paris service",
                                        "-Country:  England\n" +
                                                "-City: London\n" +
                                                "-Guests: 4\n",
                                        0,
                                        ""));
                                break;
                            case "3":
                                listChating.add(new Chating("Preferences from the Limousine" +
                                        "paris service\n",
                                        "-1 stewardess in the Limousine\n" +
                                                "-3 bottles of champagne\n",
                                        0,
                                        ""));
                                break;
                            case "4":
                                listChating.add(new Chating("Custom Fields from the Limousine" +
                                        "paris service\n",
                                        "-Type of blood: 0+\n" +
                                                "-Favorite food: Cheese\n" +
                                                "-Favorite Beer: Heineken\n",
                                        0,
                                        ""));
                                break;
                            case "5":
                                listChating.add(new Chating("Itinerary from the Limousine" +
                                        "paris service",
                                        "-12/10/22    08:23:00   Point 1  House, Calle 23 de Calacoto, La Paz, Bolivia, casa 4\n" +
                                                "-12/10/22    09:30:00   Point 2  House, de Calacoto, La Paz, Bolivia, stadium",
                                        0,
                                        ""));
                                break;
                            case "6":
                                listChating.add(new Chating("Guests from the Limousine" +
                                        "paris service",
                                        "-Guest 1   Maria Wife\n" +
                                                "-Guest 2   Paulo Friend\n" +
                                                "-Guest 3   Camilo Manager\n",
                                        0,
                                        ""));
                                break;
                            case "7":
                                listChating.add(new Chating("Attached files from the Limousine" +
                                        "paris service",
                                        "- File 1 Copy of passport   (File)\n" +
                                                "- File 2 Copy of license (File)\n",
                                        0,
                                        ""));
                                break;
                            case "8":
                                listChating.add(new Chating("Legal Documents from the Limousine" +
                                        "paris service",
                                        "- File 1 Contract  (File)",
                                        0,
                                        ""));
                                break;
                        }
                    }
                    adapterChating.notifyDataSetChanged();
                    rvchat.smoothScrollToPosition(listChating.size());
                }
            }, 700);
            adapterChating.notifyDataSetChanged();
            rvchat.smoothScrollToPosition(listChating.size());
            textChat.setText("");

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvchat.setLayoutManager(linearLayoutManager);
        rvchat.setAdapter(adapterChating);
    }
}