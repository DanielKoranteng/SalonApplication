package com.example.salonapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterPage extends AppCompatActivity {
    private static final String TAG = "RegisterPage";

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private TextInputEditText fullNameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private ProgressDialog progressDialog; // ProgressDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        fullNameEditText = findViewById(R.id.fullname_input);
        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering, please wait...");
        progressDialog.setCancelable(false); // Prevent dismissing while loading
    }

    private void registerUser() {
        String name = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter full name, email, and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show ProgressDialog
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    saveUserDataToDatabase(user.getUid(), name, email); // Save user data
                }
            } else {
                progressDialog.dismiss(); // Hide ProgressDialog
                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed";
                Toast.makeText(RegisterPage.this, "Registration failed: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Registration error", task.getException());
            }
        });
    }

    private void saveUserDataToDatabase(String userId, String fullName, String email) {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("fullName", fullName);
        userMap.put("email", email);

        databaseReference.child(userId).setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "User data saved successfully");
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    sendEmailVerification(user);
                }
            } else {
                progressDialog.dismiss(); // Hide ProgressDialog
                Log.e(TAG, "Failed to save user data", task.getException());
                Toast.makeText(RegisterPage.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(verificationTask -> {
            progressDialog.dismiss(); // Hide ProgressDialog

            if (verificationTask.isSuccessful()) {
                Toast.makeText(RegisterPage.this, "Verification email sent. Please check your email.", Toast.LENGTH_LONG).show();
                mAuth.signOut(); // Log out the user so they verify first
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                finish();
            } else {
                Toast.makeText(RegisterPage.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Email verification error", verificationTask.getException());
            }
        });
    }

    public void loginPage(View view) {
        Log.d(TAG, "Navigating to LoginPage");
        startActivity(new Intent(RegisterPage.this, LoginPage.class));
    }

    public void backHome(View view) {
        Log.d(TAG, "Navigating to MainActivity");
        startActivity(new Intent(RegisterPage.this, MainActivity.class));
    }
}
