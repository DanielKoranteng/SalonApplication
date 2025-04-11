package com.example.salonapplication;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SelectProfessionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_professional_activity);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        // Create a list of professionals
        List<Professional> professionalList = new ArrayList<>();
        professionalList.add(new Professional(
            "Any professional", 
            "4.5 ★", 
            "All Services", 
            "Various Experience", 
            "https://example.com/default.jpg"
        ));
        professionalList.add(new Professional(
            "Silvia", 
            "4.7 ★", 
            "Hair Styling", 
            "5 years", 
            "https://example.com/silvia.jpg"
        ));
        professionalList.add(new Professional(
            "Andrea", 
            "4.9 ★", 
            "Coloring Specialist", 
            "7 years", 
            "https://example.com/andrea.jpg"
        ));
        professionalList.add(new Professional(
            "Marcos Junior Barber", 
            "4.9 ★", 
            "Men's Grooming", 
            "6 years", 
            "https://example.com/marcos.jpg"
        ));
        professionalList.add(new Professional(
            "Mattia Barber", 
            "4.8 ★", 
            "Beard Styling", 
            "4 years", 
            "https://example.com/mattia.jpg"
        ));

        // Set up the adapter
        ProfessionalAdapter adapter = new ProfessionalAdapter(professionalList, this);
        recyclerView.setAdapter(adapter);
    }
    public void onProfessionalSelected(Professional professional) {
        // Save the selected professional
        String selectedProfessionalName = professional.getName();

        // Show a brief selection feedback
        Toast.makeText(this, "Selected: " + selectedProfessionalName, Toast.LENGTH_SHORT).show();

        // Create intent to navigate to the date/time selection page
        Intent intent = new Intent(this, DateTimeSelectionActivity.class);

        // Pass the selected professional's data to the next activity
        intent.putExtra("SELECTED_PROFESSIONAL_NAME", selectedProfessionalName);

        // Start the DateTimeSelectionActivity
        startActivity(intent);
    }
}