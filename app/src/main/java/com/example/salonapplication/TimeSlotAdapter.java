package com.example.salonapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * Adapter for displaying and managing time slot selection
 */
public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    // Data
    private List<String> timeSlots;
    private int selectedPosition = -1; // No selection by default

    // Listener for time slot selection events
    private OnTimeSlotClickListener listener;

    /**
     * Interface for time slot selection callback
     */
    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(String timeSlot);
    }

    /**
     * Constructor
     * @param timeSlots List of time slots to display
     */
    public TimeSlotAdapter(List<String> timeSlots) {
        this.timeSlots = timeSlots;
    }

    /**
     * Update the time slots and refresh the view
     * @param newTimeSlots New list of time slots
     */
    public void updateTimeSlots(List<String> newTimeSlots) {
        this.timeSlots = newTimeSlots;
        selectedPosition = -1; // Reset selection
        notifyDataSetChanged();
    }

    /**
     * Set the click listener
     */
    public void setOnTimeSlotClickListener(OnTimeSlotClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        String timeSlot = timeSlots.get(position);
        holder.bind(timeSlot, position);
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    /**
     * ViewHolder for time slot items
     */
    class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView timeSlotText;

        TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.timeSlotCard);
            timeSlotText = itemView.findViewById(R.id.timeSlotText);

            // Set up click listener
            cardView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Update selected position
                    int previousPosition = selectedPosition;
                    selectedPosition = position;

                    // Notify item changes to update UI
                    notifyItemChanged(previousPosition);
                    notifyItemChanged(selectedPosition);

                    // Trigger callback
                    if (listener != null) {
                        listener.onTimeSlotClick(timeSlots.get(position));
                    }
                }
            });
        }

        /**
         * Bind data to the view
         */
        void bind(String timeSlot, int position) {
            timeSlotText.setText(timeSlot);

            // Set selected state
            boolean isSelected = position == selectedPosition;
            cardView.setChecked(isSelected);
            cardView.setStrokeWidth(isSelected ? 2 : 1);
        }
    }
}