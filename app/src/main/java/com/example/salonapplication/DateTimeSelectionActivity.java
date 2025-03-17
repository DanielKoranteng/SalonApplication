package com.example.salonapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTimeSelectionActivity extends AppCompatActivity {

    // UI components
    private TextView selectedDateText;
    private RecyclerView timeSlotRecyclerView;
    private MaterialButton selectDateButton;
    private MaterialButton continueButton;

    // Data
    private long selectedDate = 0;
    private String selectedTimeSlot = null;
    private TimeSlotAdapter timeSlotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_date_time_selection);

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the selected professional's name from intent
        String selectedProfessionalName = getIntent().getStringExtra("SELECTED_PROFESSIONAL_NAME");

        // Update UI to show the selected professional
        TextView selectedProfessionalText = findViewById(R.id.selectedProfessionalText);
        if (selectedProfessionalText != null && selectedProfessionalName != null) {
            selectedProfessionalText.setText("Selected: " + selectedProfessionalName);
        }

        // Initialize UI components
        initializeViews();

        // Setup date/time selection UI components
        setupDateTimeSelection();
    }

    /**
     * Initialize all view references
     */
    private void initializeViews() {
        selectedDateText = findViewById(R.id.selectedDateText);
        selectDateButton = findViewById(R.id.selectDateButton);
        timeSlotRecyclerView = findViewById(R.id.timeSlotRecyclerView);
        continueButton = findViewById(R.id.continueButton);
    }

    /**
     * Set up the date and time selection components
     */
    private void setupDateTimeSelection() {
        // 1. Set up date selection
        setupDateSelection();

        // 2. Set up time slot selection
        setupTimeSlotSelection();

        // 3. Set up continue button
        setupContinueButton();
    }

    /**
     * Configure the date picker and selection functionality
     */
    private void setupDateSelection() {
        // Set today's date as default
        selectedDate = System.currentTimeMillis();
        updateSelectedDateText(selectedDate);

        // Configure date selection button
        selectDateButton.setOnClickListener(v -> showDatePicker());
    }

    /**
     * Show the Material Date Picker dialog
     */
    /**
     * Show the Material Date Picker dialog
     * This version ensures that only present and future dates can be selected.
     */
    /**
     * Show the Material Date Picker dialog
     * Ensures only present and future dates can be selected.
     */
    private void showDatePicker() {
        // Get today's date (start of the day)
        long today = System.currentTimeMillis();

        // Apply constraints to disable past dates
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()); // Ensures only today and future dates

        // Create the date picker with constraints
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Appointment Date")
                .setSelection(today) // Default selection: today
                .setCalendarConstraints(constraintsBuilder.build()) // Prevent past dates
                .build();

        // Handle date selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate = selection;
            updateSelectedDateText(selection);
            updateAvailableTimeSlots(selection); // Refresh available time slots
        });

        // Show the picker
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }



    /**
     * Update the UI to display the selected date
     */
    private void updateSelectedDateText(long dateInMillis) {
        Date date = new Date(dateInMillis);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault());
        selectedDateText.setText(format.format(date));
    }

    /**
     * Set up the time slot selection functionality
     */
    private void setupTimeSlotSelection() {
        // Create adapter with time slot data
        timeSlotAdapter = new TimeSlotAdapter(generateTimeSlots());

        // Set up RecyclerView with Grid Layout (3 columns)
        timeSlotRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        timeSlotRecyclerView.setAdapter(timeSlotAdapter);

        // Set up selection listener
        timeSlotAdapter.setOnTimeSlotClickListener(timeSlot -> {
            // Store selected time
            selectedTimeSlot = timeSlot;
        });
    }

    /**
     * Update available time slots based on selected date
     * In a real app, this would fetch data from a server or database
     */
    private void updateAvailableTimeSlots(long selectedDate) {
        // For this example, we'll just regenerate the time slots
        // In a real app, you would query available slots for the selected date
        timeSlotAdapter.updateTimeSlots(generateTimeSlots());
    }

    /**
     * Generate time slots from 9 AM to 5 PM
     */
    private List<String> generateTimeSlots() {
        List<String> slots = new ArrayList<>();
        slots.add("9:00 AM");
        slots.add("10:00 AM");
        slots.add("11:00 AM");
        slots.add("12:00 PM");
        slots.add("1:00 PM");
        slots.add("2:00 PM");
        slots.add("3:00 PM");
        slots.add("4:00 PM");
        slots.add("5:00 PM");
        slots.add("6:00 PM");
        slots.add("7:00 PM");
        slots.add("8:00 PM");
        return slots;
    }

    /**
     * Set up the continue button functionality
     */
    private void setupContinueButton() {
        continueButton.setOnClickListener(v -> {
            if (selectedTimeSlot == null) {
                Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
                return;
            }

            // Process the selected date and time
            processDateTimeSelection();
        });
    }

    /**
     * Process the final date and time selection
     * In a real app, this would create an appointment and navigate to the next screen
     */
    private void processDateTimeSelection() {
        // Format the selected date and time for display
        Date date = new Date(selectedDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(date);

        // Show confirmation or proceed to next step
        String message = "Appointment scheduled for " + formattedDate + " at " + selectedTimeSlot;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Here you would typically:
        // 1. Save the appointment to your database
        // 2. Navigate to a confirmation screen
        // For example:
        // Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        // intent.putExtra("APPOINTMENT_DATE", formattedDate);
        // intent.putExtra("APPOINTMENT_TIME", selectedTimeSlot);
        // startActivity(intent);
    }
}