package com.hyggs.clientchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyggs.clientchat.R;
import com.hyggs.clientchat.models.Chating;
import com.hyggs.clientchat.models.Chats;

import java.util.ArrayList;

public class AdapterChating extends RecyclerView.Adapter<AdapterChating.ChatsViewHolder> {

    private final ArrayList<Chating> listChating;
    private final int resource;
    private final Context context;

    public AdapterChating(ArrayList<Chating> chats, int resource, Context mContext){
        this.listChating = chats;
        this.resource = resource;
        this.context = mContext;
    }

    @NonNull
    @Override
    public AdapterChating.ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChating.ChatsViewHolder holder, int position) {
        holder.bindData(listChating.get(position));
    }

    @Override
    public int getItemCount() {
        return listChating.size();
    }


    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

        private final TextView titlere,titleem;
        private final TextView contentre,contentem;
        private  LinearLayout viewre,viewem;
        public RelativeLayout viewChatRelativeLayout, viewDeleteRelativeLayout;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            viewre = itemView.findViewById(R.id.viewre);
            viewem = itemView.findViewById(R.id.viewem);
            titlere = itemView.findViewById(R.id.tvtitlechatre);
            titleem = itemView.findViewById(R.id.tvtitlechatem);

            contentre = itemView.findViewById(R.id.contentre);
            contentem = itemView.findViewById(R.id.tvcontentem);
            viewChatRelativeLayout = itemView.findViewById(R.id.viewChatRelativeLayout);
            viewDeleteRelativeLayout = itemView.findViewById(R.id.viewDeleteRelativeLayout);

        }

        void bindData(final Chating item){
            if (item.getType()==0){
                viewre.setVisibility(View.VISIBLE);
                viewem.setVisibility(View.GONE);
                titlere.setText(item.getTitle());
                contentre.setText(item.getContent());
            }else {
                viewre.setVisibility(View.GONE);
                viewem.setVisibility(View.VISIBLE);
                titleem.setText(item.getTitle());
            }
        }

    }

    public void removeChat(int position){
        listChating.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreChat(Chating chats, int position){
        listChating.add(position, chats);
        notifyItemInserted(position);
    }
}
