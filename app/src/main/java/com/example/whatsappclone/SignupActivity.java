package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


        binding.alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.suUserName.getText().toString())){
                    binding.suUserName.setError("Username is required");
                }else if(TextUtils.isEmpty(binding.suEmail.getText().toString())){
                    binding.suEmail.setError("Email is required");
                }else if(TextUtils.isEmpty(binding.suPassword.getText().toString())){
                    binding.suPassword.setError("password is required");
                }else{
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

                                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(SignupActivity.this, "Failed to craete new user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }




            }
        });

        getSupportActionBar().hide();
    }
}