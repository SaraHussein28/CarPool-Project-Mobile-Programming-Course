package com.example.carpool_project.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.driver.DriverHomeActivity;
import com.example.carpool_project.ui.registration.RegistrationActivity;
import com.example.carpool_project.ui.rider_entry.FirstActivity;
import com.example.carpool_project.ui.sign_out.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final String DRIVER_ROLE = "Driver";
    private final String RIDER_ROLE = "Rider";
    private WordViewModel mWordViewModel;
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView signUpText;
    final String[] userRole = new String[1];

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
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editText00);
        editTextPassword = findViewById(R.id.editText01);
        buttonLogin = findViewById(R.id.button01);
        progressBar = findViewById(R.id.progressBar00);
        signUpText = findViewById(R.id.textView04);
        mAuth = FirebaseAuth.getInstance();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText, passwordText;
                emailText = String.valueOf(editTextEmail.getText());
                passwordText = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login succeeded",
                                            Toast.LENGTH_SHORT).show();

                                    findUserRole(mAuth);
                                    // Sign in success, update UI with the signed-in user's information
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }

    private void navigateNextActivity(String userRole) {
        if (userRole.equals(DRIVER_ROLE)) {
            Intent intent = new Intent(getApplicationContext(), DriverHomeActivity.class);
            startActivity(intent);
            finish();
        } else if (userRole.equals(RIDER_ROLE)) {
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void findUserRole(FirebaseAuth mAuth) {

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getUserDataFromFirebase(mAuth.getCurrentUser().getUid()).observe(this, words -> {
            userRole[0] = words.getUserRole();
            navigateNextActivity(userRole[0]);
        });
    }
}