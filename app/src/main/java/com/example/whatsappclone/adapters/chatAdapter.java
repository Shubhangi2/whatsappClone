package com.example.whatsappclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.model.users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.myViewHolder>{

    ArrayList<users> list;
    Context context;


    public chatAdapter(ArrayList<users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_singlelayout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        users users_obj = list.get(position);
        Picasso.get().load(users_obj.getProfileImg()).placeholder(R.drawable.profile_image).into(holder.userImg);
        holder.name.setText(users_obj.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView name, lastMessage;
        ImageView userImg;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.chat_name);
            lastMessage = (TextView) itemView.findViewById(R.id.chat_lastMessage);
            userImg = (ImageView) itemView.findViewById(R.id.chat_image);

        }
    }
}
