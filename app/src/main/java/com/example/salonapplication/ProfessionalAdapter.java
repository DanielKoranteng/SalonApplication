package com.example.salonapplication;
 

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ProfessionalViewHolder> {

    private final List<Professional> professionalList;
    private final SelectProfessionalActivity listener;

    // Constructor to initialize the adapter with the list and listener
    public ProfessionalAdapter(List<Professional> professionalList, SelectProfessionalActivity listener) {
        this.professionalList = professionalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfessionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professional, parent, false);
        return new ProfessionalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalViewHolder holder, int position) {
        // Bind data to the views
        Professional professional = professionalList.get(position);
        holder.professionalName.setText(professional.getName());
        holder.professionalRating.setText(professional.getRating());

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProfessionalSelected(professional); // Notify listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return professionalList.size(); // Return the size of the list
    }

    // ViewHolder class
    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder {
        TextView professionalName, professionalRating;

        public ProfessionalViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            professionalName = itemView.findViewById(R.id.professionalName);
            professionalRating = itemView.findViewById(R.id.professionalRating);
        }
    }
}