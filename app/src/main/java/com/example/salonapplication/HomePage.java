package com.example.salonapplication;



import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;



public class HomePage extends AppCompatActivity {
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        scrollView = findViewById(R.id.scrollView); // Ensure this matches the ID in your layout
    }

    public void scrollToTeamSection(View view) {
        ScrollView scrollView = findViewById(R.id.scrollView);
        View teamSection = findViewById(R.id.teamSection);
        scrollView.smoothScrollTo(0, teamSection.getTop());
    }

    public void scrollToAboutSection(View view) {
        ScrollView scrollView = findViewById(R.id.scrollView);
        View aboutSection = findViewById(R.id.aboutSection);
        scrollView.smoothScrollTo(0, aboutSection.getTop());
    }

    public void scrollToServiceSection(View view) {
        ScrollView scrollView = findViewById(R.id.scrollView);
        View serviceSection = findViewById(R.id.serviceSection);
        scrollView.smoothScrollTo(0, serviceSection.getTop());
    }
}