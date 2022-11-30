package com.hyggs.clientchat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.activities.ChatActivity;
import com.hyggs.clientchat.models.Chating;
import com.hyggs.clientchat.models.Chats;
import com.hyggs.clientchat.models.Language;

import java.util.ArrayList;

public class AdapterChatsRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  ArrayList<Chats> listChats;
    private  Context context;
    private LayoutInflater inflater;

    public AdapterChatsRecyclerView(ArrayList<Chats> chats, Context mContext){
        this.listChats = chats;
        this.context = mContext;
        inflater= LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_chat,parent,false);
        AdapterChatsRecyclerView.ChatsViewHolder chatsViewHolder = new AdapterChatsRecyclerView.ChatsViewHolder(view);
        return chatsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsViewHolder chatsViewHolder = (ChatsViewHolder) holder;
        Chats chats = listChats.get(position);
        if (chats.getType().equals("0")){
            chatsViewHolder.llnextitem.setVisibility(View.VISIBLE);
            chatsViewHolder.itemChat.setVisibility(View.GONE);
            chatsViewHolder.newtitle.setText(chats.getTitle());
            chatsViewHolder.newcontent.setText(chats.getContent());
            chatsViewHolder.newlastime.setText(chats.getLasttime());
        }else{
            chatsViewHolder.llnextitem.setVisibility(View.GONE);
            chatsViewHolder.itemChat.setVisibility(View.VISIBLE);
            chatsViewHolder.title.setText(chats.getTitle());
            chatsViewHolder.status.setText(chats.getStatus());
            chatsViewHolder.content.setText(chats.getContent());
            chatsViewHolder.type.setText(chats.getType());
            chatsViewHolder.lasttime.setText(chats.getLasttime());
        }

    }

    @Override
    public int getItemCount() {
        return listChats.size();
    }



    class ChatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  TextView title;
        private  TextView status;
        private TextView content;
        private TextView type;
        private TextView lasttime;
        private LinearLayout llnextitem;
        private TextView newtitle,newcontent,newlastime;
        public RelativeLayout viewChatRelativeLayout, viewDeleteRelativeLayout;
        private CardView itemChat;
        public ChatsViewHolder(View itemView) {
            super(itemView);
            itemChat = itemView.findViewById(R.id.itemChat);
            title = itemView.findViewById(R.id.titleTextView);
            content = itemView.findViewById(R.id.txtcontent);
            type= itemView.findViewById(R.id.txttype);
            llnextitem = itemView.findViewById(R.id.llnextitem);
            newtitle = itemView.findViewById(R.id.newitemtitle);
            newcontent = itemView.findViewById(R.id.newitemcontent);
            newlastime = itemView.findViewById(R.id.newitemlastime);
            lasttime= itemView.findViewById(R.id.txtlastime);
            status = itemView.findViewById(R.id.statusTextView);
            viewChatRelativeLayout = itemView.findViewById(R.id.viewChatRelativeLayout);
            viewDeleteRelativeLayout = itemView.findViewById(R.id.viewDeleteRelativeLayout);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, ChatActivity.class));
            //Toast.makeText(context, ""+listChats.get(getAbsoluteAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeChat(int position){
        listChats.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchitem(ArrayList<Chats> chats){
        this.listChats = chats;
        notifyDataSetChanged();

    }
    public void restoreChat(Chats chats, int position){
        listChats.add(position, chats);
        notifyItemInserted(position);
    }
}
