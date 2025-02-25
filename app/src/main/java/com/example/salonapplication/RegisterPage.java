package com.example.salonapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterPage extends AppCompatActivity {
    private static final String TAG = "RegisterPage";
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
    }

    public void loginPage(View view) {
        Log.d(TAG, "connect: Navigating to LoginPage");
        startActivity(new Intent(RegisterPage.this, LoginPage.class));
    }

    public void backHome(View view) {
        Log.d(TAG, "connect: Navigating to MainActivity");
        startActivity(new Intent(RegisterPage.this, MainActivity.class));
    }

    public void loginPage2(View view) {
        Log.d(TAG, "connect: Navigating to LoginPage");
        startActivity(new Intent(RegisterPage.this, LoginPage.class));
    }
}