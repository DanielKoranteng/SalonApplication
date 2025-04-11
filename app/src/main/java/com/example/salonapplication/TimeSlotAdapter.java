package com.example.salonapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    private List<TimeSlot> timeSlots;
    private OnTimeSlotClickListener listener;
    private int selectedPosition = -1;

    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(TimeSlot timeSlot);
    }

    public TimeSlotAdapter(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
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
        TimeSlot timeSlot = timeSlots.get(position);
        holder.bind(timeSlot, position == selectedPosition);
        
        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;
            
            int previousSelected = selectedPosition;
            selectedPosition = currentPosition;
            
            if (previousSelected != -1) {
                notifyItemChanged(previousSelected);
            }
            notifyItemChanged(selectedPosition);
            
            if (listener != null) {
                listener.onTimeSlotClick(timeSlots.get(currentPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public void setOnTimeSlotClickListener(OnTimeSlotClickListener listener) {
        this.listener = listener;
    }

    public void updateTimeSlots(List<TimeSlot> newTimeSlots) {
        int oldSelectedPosition = selectedPosition;
        selectedPosition = -1;
        
        this.timeSlots = newTimeSlots;
        
        if (oldSelectedPosition != -1) {
            notifyItemChanged(oldSelectedPosition);
        }
        notifyItemRangeChanged(0, newTimeSlots.size());
    }

    static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeText;
        private final TextView availabilityText;
        private final TextView durationText;

        TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.timeSlotText);
            availabilityText = itemView.findViewById(R.id.availabilityText);
            durationText = itemView.findViewById(R.id.durationText);
        }

        void bind(TimeSlot timeSlot, boolean isSelected) {
            timeText.setText(timeSlot.getTime());
            availabilityText.setText(timeSlot.getAvailableSpots() + " spots");
            durationText.setText(timeSlot.getFormattedDuration());
            itemView.setSelected(isSelected);
        }
    }
}