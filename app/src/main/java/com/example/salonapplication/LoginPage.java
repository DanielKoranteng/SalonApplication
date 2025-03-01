package com.example.salonapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    public static final String TAG = "LoginPage";
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog; // ProgressDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> checkUserExists());

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait...");
        progressDialog.setCancelable(false); // Prevent dismissing while loading
    }

    private void checkUserExists() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show ProgressDialog
        progressDialog.show();

        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginUser(email, password);
                } else {
                    progressDialog.dismiss(); // Hide ProgressDialog
                    Toast.makeText(LoginPage.this, "User not found. Please register first.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss(); // Hide ProgressDialog
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(LoginPage.this, "Database error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressDialog.dismiss(); // Hide ProgressDialog

            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null && user.isEmailVerified()) {
                    Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this, HomePage.class));
                    finish();
                } else {
                    Toast.makeText(LoginPage.this, "Please verify your email before logging in", Toast.LENGTH_LONG).show();
                }
            } else {
                String errorMsg = "Login failed. Please check your email and password.";
                if (task.getException() != null) {
                    errorMsg += " " + task.getException().getMessage();
                }
                Toast.makeText(LoginPage.this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void backHome(View view) {
        Log.d(TAG, "Navigating to MainActivity");
        startActivity(new Intent(LoginPage.this, MainActivity.class));
    }

    public void homePage(View view) {
        Log.d(TAG, "Navigating to HomePage");
        startActivity(new Intent(LoginPage.this, HomePage.class));
    }

    public void registerPage(View view) {
        Log.d(TAG, "Navigating to LoginPage");
        startActivity(new Intent(LoginPage.this, RegisterPage.class));
    }
}
