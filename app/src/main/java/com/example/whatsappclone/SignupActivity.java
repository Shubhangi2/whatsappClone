package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySignupBinding;
import com.example.whatsappclone.model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("Please wait! we are creating your account.");

        binding.suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword
                        (binding.suEmail.getText().toString(), binding.suPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                    users user = new users(binding.suUserName.getText().toString(), binding.suEmail.getText().toString(),
                                            binding.suPassword.getText().toString());

                                    String id = task.getResult().getUser().getUid();

                                    FirebaseDatabase.getInstance().getReference().child("Users").child(id).setValue(user);




                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, "Failed to craete new user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        getSupportActionBar().hide();
    }
}