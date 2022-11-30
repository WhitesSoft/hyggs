package com.hyggs.clientchat.fragments;

import static com.hyggs.clientchat.activities.HomeChatActivity.binding;
import static com.hyggs.clientchat.utilities.Utilities.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hyggs.clientchat.R;
import com.hyggs.clientchat.activities.ChatActivity;
import com.hyggs.clientchat.adapters.AdapterChatsRecyclerView;
import com.hyggs.clientchat.adapters.CallBackItemTouch;
import com.hyggs.clientchat.adapters.ChatTouchHelper;
import com.hyggs.clientchat.models.Chats;

import java.util.ArrayList;
import java.util.Objects;


public class MenuHomeChatFragment extends Fragment implements CallBackItemTouch {

    private RecyclerView chatsRecyclerView;
    private AdapterChatsRecyclerView adapterChatsRecyclerView;
    ArrayList<Chats> listChats = new ArrayList<>();
    RelativeLayout layout;
    LinearLayout searchll;
    TextView canceltv,clearText;
    EditText editTextSearch;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_home_chat, container, false);

        searchll = view.findViewById(R.id.llsearch);
        canceltv = view.findViewById(R.id.txtcancel);
        clearText = view.findViewById(R.id.cleartv);
        editTextSearch = view.findViewById(R.id.edittextsearch);
        ImageView newChat = view.findViewById(R.id.newChatButton);
        ImageView dontHaveChats = view.findViewById(R.id.dontHaveChats);
        chatsRecyclerView = view.findViewById(R.id.recyclerView);
        layout = view.findViewById(R.id.contentChats);

        newChat.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("typechat","bot");
            startActivity(intent);
        });

        //setup vista recyclerview
        addData();

        if (listChats.size() > 0){
            dontHaveChats.setVisibility(View.GONE);

        }



        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.toolbar.setVisibility(View.GONE);
                canceltv.setVisibility(View.VISIBLE);
                canceltv.setText(Html.fromHtml(getResources().getString(R.string.cancelar)));
                clearText.setVisibility(View.VISIBLE);
            }
        });
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    binding.toolbar.setVisibility(View.GONE);
                    canceltv.setVisibility(View.VISIBLE);
                    canceltv.setText(Html.fromHtml(getResources().getString(R.string.cancelar)));
                    clearText.setVisibility(View.VISIBLE);
                }else if (editTextSearch.getText().length() == 0){
                    binding.toolbar.setVisibility(View.VISIBLE);
                    canceltv.setVisibility(View.GONE);
                    clearText.setVisibility(View.GONE);
                    hideKeyboard(requireActivity());
                }else{
                    binding.toolbar.setVisibility(View.VISIBLE);
                    canceltv.setVisibility(View.GONE);
                    clearText.setVisibility(View.GONE);
                    hideKeyboard(requireActivity());
                }
            }
        });
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    hideKeyboard(requireActivity());
                    SearchInChat(editTextSearch.getText().toString(),listChats);
                    return true;
                }
                return false;
            }
        });
        canceltv.setOnClickListener(v ->{
            binding.toolbar.setVisibility(View.VISIBLE);
            canceltv.setVisibility(View.GONE);
            clearText.setVisibility(View.GONE);
            editTextSearch.setText("");
            hideKeyboard(requireActivity());
            addData();
        });
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextSearch.getText().length()!=0){
                    editTextSearch.setText("");
                }
            }
        });

        return view;
    }

    private void addData() {

        if (!listChats.isEmpty()){
            listChats.clear();
        }
        listChats.add(new Chats("FRANCE TOUR", "Charter","Service","Open",""+"9 min"));
        listChats.add(new Chats("TRANSPORTATION", "Limousine","Requirement: 1203","Open",""+"9 min"));
        listChats.add(new Chats("TRANSPORTATION", "Limousine","Service: 1206","Open",""+"9 min"));
        listChats.add(new Chats("PARIS TRIP", "Charter","Service: 2133","Open",""+"17 min"));
        listChats.add(new Chats("CHRISTMAS VACATION", "Hotel","Requirement: 1108","Open",""+"13 min"));
        listChats.add(new Chats("PARIS TRIP", "Charter","Service: 2133","Open",""+"17 min"));
        listChats.add(new Chats("CHRISTMAS VACATION", "Hotel","Service: 1108","Open",""+"13 min"));
        listChats.add(new Chats("PARIS TRIP", "Charter","Requirements: 1234","Open",""+"17 min"));
        listChats.add(new Chats("PARIS TRIP", "Charter","Service: 2133","Open",""+"17 min"));
        listChats.add(new Chats("CHRISTMAS VACATION", "Hotel","Service: 1108","Open",""+"13 min"));
        listChats.add(new Chats("PARIS TRIP", "Charter","Service: 2133","Open",""+"17 min"));
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatsRecyclerView.setHasFixedSize(true);
        adapterChatsRecyclerView = new AdapterChatsRecyclerView(listChats, getActivity());
        chatsRecyclerView.setAdapter(adapterChatsRecyclerView);
        ItemTouchHelper.Callback callback = new ChatTouchHelper(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(chatsRecyclerView);
    }

    private void SearchInChat(String key,ArrayList<Chats> chats){
        //listChats.clear();
        ArrayList<Chats> filteredlist = new ArrayList<Chats>();
        for (Chats elements: chats){
            if (elements.getContent().toLowerCase().contains(key.toLowerCase())){
                filteredlist.add(new Chats(elements.getTitle(), elements.getContent(),
                        "0", elements.getStatus(), elements.getLasttime()));
            }
        }
       if (filteredlist.isEmpty()){
           listChats.clear();
           Toast.makeText(requireActivity(), "Sin resultados", Toast.LENGTH_SHORT).show();
       }else{
           adapterChatsRecyclerView.searchitem(filteredlist);
       }
    }


    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {

        listChats.add(newPosition, listChats.remove(oldPosition));
        adapterChatsRecyclerView.notifyItemMoved(oldPosition, newPosition);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        String title = listChats.get(viewHolder.getAdapterPosition()).getTitle();

        // backup of removed item for undo
        final Chats deleteditem = listChats.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        // Eliminamos chat
        adapterChatsRecyclerView.removeChat(viewHolder.getAdapterPosition());

        //Mostramos Snackbar para confirmar
        Snackbar snackbar = Snackbar.make(layout, title+ ", remove?", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterChatsRecyclerView.restoreChat(deleteditem, deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();


    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {

        listChats.add(newPosition, listChats.remove(oldPosition));
        adapterChatsRecyclerView.notifyItemMoved(oldPosition, newPosition);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        String title = listChats.get(viewHolder.getAdapterPosition()).getTitle();

        // backup of removed item for undo
        final Chats deleteditem = listChats.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        // Eliminamos chat
        adapterChatsRecyclerView.removeChat(viewHolder.getAdapterPosition());

        //Mostramos Snackbar para confirmar
        Snackbar snackbar = Snackbar.make(layout, title+ ", remove?", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterChatsRecyclerView.restoreChat(deleteditem, deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();


    }
}