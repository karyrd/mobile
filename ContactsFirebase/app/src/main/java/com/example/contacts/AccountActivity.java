package com.example.contacts;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);

        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("asd", "signed in: " + user.getUid());
                } else {
                    Log.d("asd", "signed out");
                }
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SignIn(emailField.getText().toString(), passwordField.getText().toString());
                } catch (Exception ex) {
                    Toast.makeText(AccountActivity.this,
                            "An error occurred: " + ex.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SignUp(emailField.getText().toString(), passwordField.getText().toString());
                } catch (Exception ex) {
                    Toast.makeText(AccountActivity.this,
                            "An error occurred: " + ex.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void SignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(
                                    AccountActivity.this,
                                    "Signed in successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(
                                    AccountActivity.this,
                                    "Signed in failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }
    private void SignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(
                                    AccountActivity.this,
                                    "Signed up successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(
                                    AccountActivity.this,
                                    "Signed up failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("asd", "task = " + task.toString()
                                    + "task.isSuccessful() = " + task.isSuccessful());
                            Log.d("asd", "mAuth = " + mAuth.getApp().toString());
                        }
                    }
                });
    }

}
