package com.example.salonapplication;

import static com.example.salonapplication.MainActivity.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import java.util.ArrayList;
import java.util.List;

public class ServicesPage extends AppCompatActivity {
    private LinearLayout footer;
    private TextView selectedServices;
    private CheckBox checkboxTaglioOldStyle, checkboxTaglioBimbo, checkboxLaManhattan, checkboxRasatura, checkboxBarbaRitual, checkboxBarbaConPanni, checkboxRasaturaFull, checkboxLaBoston;
    private List<String> selectedServicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services_page);

        // Initialize views
        footer = findViewById(R.id.footer);
        selectedServices = findViewById(R.id.selectedServices);

        // Initialize checkboxes
        checkboxTaglioOldStyle = findViewById(R.id.checkboxTaglioOldStyle);
        checkboxTaglioBimbo = findViewById(R.id.checkboxTaglioBimbo);
        checkboxLaManhattan = findViewById(R.id.checkboxLaManhattan);
        checkboxRasatura = findViewById(R.id.checkboxRasatura);
        checkboxBarbaRitual = findViewById(R.id.checkboxBarbaRitual);
        checkboxBarbaConPanni = findViewById(R.id.checkboxBarbaConPanni);
        checkboxRasaturaFull = findViewById(R.id.checkboxRasaturaFull);
        checkboxLaBoston = findViewById(R.id.checkboxLaBoston);

        // Set up checkbox listeners
        checkboxTaglioOldStyle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Taglio Old Style", isChecked);
        });

        checkboxTaglioBimbo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Taglio Bimbo 1-13", isChecked);
        });

        checkboxLaManhattan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("La Manhattan", isChecked);
        });

        checkboxRasatura.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Rasatura a Misura Unica", isChecked);
        });

        checkboxBarbaRitual.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Barba Ritual", isChecked);
        });

        checkboxBarbaConPanni.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Barba Con Panni", isChecked);
        });

        checkboxRasaturaFull.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("Rasatura Full", isChecked);
        });

        checkboxLaBoston.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSelectedServices("La Boston", isChecked);
        });
    }

    private void updateSelectedServices(String serviceName, boolean isChecked) {
        if (isChecked) {
            selectedServicesList.add(serviceName); // Add service to the list
        } else {
            selectedServicesList.remove(serviceName); // Remove service from the list
        }

        // Update the footer visibility and selected services text
        if (selectedServicesList.isEmpty()) {
            footer.setVisibility(View.GONE); // Hide footer if no services are selected
        } else {
            footer.setVisibility(View.VISIBLE); // Show footer if at least one service is selected
            StringBuilder servicesText = new StringBuilder("Selected Services:\n");
            for (String service : selectedServicesList) {
                servicesText.append("- ").append(service).append("\n");
            }
            selectedServices.setText(servicesText.toString()); // Update the selected services text
        }
    }

    public void continueButton(View view) {
        Log.d(TAG , "Continue button clicked");
        startActivity(new Intent(ServicesPage.this, SelectProfessionalActivity.class));
    }
}