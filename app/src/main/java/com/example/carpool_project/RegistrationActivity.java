package com.example.carpool_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAge;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewLogin;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextName = findViewById(R.id.editText00);
        editTextAge = findViewById(R.id.editText01);
        editTextPhone = findViewById(R.id.editText02);
        editTextEmail = findViewById(R.id.editText03);
        editTextPassword = findViewById(R.id.editText04);
        buttonRegister = findViewById(R.id.button00);
        progressBar = findViewById(R.id.progressBar00);
        textViewLogin = findViewById(R.id.textView07);
        mAuth = FirebaseAuth.getInstance();

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            // TODO: add input validation function to check that the entered email matched FOE - ASU Domain.
            @Override
            public void onClick(View v) {
                String nameText, ageText, phoneText, emailText, passwordText;

                nameText = String.valueOf(editTextName.getText());
                ageText = String.valueOf(editTextAge.getText());
                phoneText = String.valueOf(editTextPhone.getText());
                emailText = String.valueOf(editTextEmail.getText());
                passwordText = String.valueOf(editTextPassword.getText());


                if (TextUtils.isEmpty(nameText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(ageText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your age", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Authentication Succeeded.", Toast.LENGTH_SHORT).show();
                            storeNewUserDate();
                            // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }

    private void storeNewUserDate() {
    }
}