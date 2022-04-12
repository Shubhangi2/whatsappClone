package com.example.whatsappclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapters.chatAdapter;
import com.example.whatsappclone.databinding.FragmentChatsBinding;
import com.example.whatsappclone.model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
    FragmentChatsBinding bindng;

    ArrayList<users> list = new ArrayList<>();


    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindng = FragmentChatsBinding.inflate(inflater, container, false);

        chatAdapter adapter = new chatAdapter(list, getContext());

        bindng.chatsRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));
        bindng.chatsRecylerview.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            users user = dataSnapshot.getValue(users.class);
                            user.getUserId(dataSnapshot.getKey());
                            list.add(user);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        return bindng.getRoot();
    }
}