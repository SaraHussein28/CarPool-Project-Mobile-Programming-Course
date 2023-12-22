package com.example.carpool_project.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.entities.User;
import com.example.carpool_project.ui.helpers.UserHelperClass;
import com.example.carpool_project.ui.login.LoginActivity;
import com.example.carpool_project.ui.sign_out.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    private static final String FACULTY_DOMAIN = "@eng.asu.edu.eg";
    private WordViewModel mWordViewModel;
    ArrayAdapter<String> userRoleArrayAdapter;
    AutoCompleteTextView userRoleAutoCompleteTextView;
    EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextGender, editTextUsername;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewLogin;
    String[] userRoleList = {"Rider", "Driver"};
    private String selectedUserRole;

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
        findViewsById();
        setClickListeners();
        createArrayAdapters();
        setArrayAdapters();
        mAuth = FirebaseAuth.getInstance();

    }

    private void setArrayAdapters() {
        userRoleAutoCompleteTextView.setAdapter(userRoleArrayAdapter);
    }

    private void createArrayAdapters() {
        userRoleArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, userRoleList);

    }

    private void setClickListeners() {

        userRoleAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedUserRole = item;
                Toast.makeText(RegistrationActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });
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
                String nameText, genderText, phoneText, emailText, passwordText, userName;
                userName = String.valueOf(editTextUsername.getText());
                nameText = String.valueOf(editTextName.getText());
                genderText = String.valueOf(editTextGender.getText());
                phoneText = String.valueOf(editTextPhone.getText());
                emailText = String.valueOf(editTextEmail.getText());
                passwordText = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(selectedUserRole)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your role", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nameText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(genderText)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your gender", Toast.LENGTH_SHORT).show();
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

                if (!(emailText.endsWith(FACULTY_DOMAIN))) {
                    Toast.makeText(RegistrationActivity.this, "Please enter a valid email belonging to faculty of engineering", Toast.LENGTH_SHORT).show();
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
                            storeNewUserDate(selectedUserRole, nameText, userName, genderText, phoneText, emailText, passwordText);
                            storeNewUserIntoRoom(selectedUserRole, nameText, userName, genderText, phoneText, emailText, passwordText);
                            // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();

                           Log.d("signup",  mAuth.getCurrentUser().getUid());
//                            if (Objects.equals(selectedUserRole, DRIVER_ROLE)){
//                                // Set admin privilege on the user corresponding to uid.
//                                Map<String, Object> claims = new HashMap<>();
//                                claims.put("driver", true);
//
//                                mAuth.setCustomUserClaims(mAuth.getCurrentUser().getUid(), claims);
//                            }
//                            else if (Objects.equals(selectedUserRole, RIDER_ROLE)){
//
//                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void findViewsById() {
        editTextName = findViewById(R.id.editText00);
        editTextUsername = findViewById(R.id.editText08);
        editTextGender = findViewById(R.id.editText01);
        editTextPhone = findViewById(R.id.editText02);
        editTextEmail = findViewById(R.id.editText03);
        editTextPassword = findViewById(R.id.editText04);
        buttonRegister = findViewById(R.id.button00);
        progressBar = findViewById(R.id.progressBar00);
        textViewLogin = findViewById(R.id.textView07);
        userRoleAutoCompleteTextView = findViewById(R.id.autoCompleteTextView00);
    }

    private void storeNewUserIntoRoom(String selectedUserRole, String nameText, String userName, String genderText, String phoneText, String emailText, String passwordText) {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        User user = new User(selectedUserRole, userName, nameText, genderText, passwordText, phoneText, emailText);
        mWordViewModel.insertUser(user);
    }

    private void storeNewUserDate(String selectedUserRole, String nameText, String userName, String genderText, String phoneText, String emailText, String passwordText) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference reference = rootNode.getReference("Users");
        UserHelperClass user = new UserHelperClass(selectedUserRole, nameText, userName, emailText, phoneText, passwordText, genderText);
        reference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "successfully inserted into database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "congrats");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "hard luck");

                }
            }
        });
    }
}