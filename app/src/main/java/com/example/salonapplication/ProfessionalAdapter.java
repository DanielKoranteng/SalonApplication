package com.example.salonapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ProfessionalViewHolder> {

    private List<Professional> professionalList;

    public ProfessionalAdapter(List<Professional> professionalList) {
        this.professionalList = professionalList;
    }

    @NonNull
    @Override
    public ProfessionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professional, parent, false);
        return new ProfessionalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalViewHolder holder, int position) {
        Professional professional = professionalList.get(position);
        holder.professionalName.setText(professional.getName());
        holder.professionalRating.setText(professional.getRating());
    }

    @Override
    public int getItemCount() {
        return professionalList.size();
    }

    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder {
        TextView professionalName, professionalRating;

        public ProfessionalViewHolder(@NonNull View itemView) {
            super(itemView);
            professionalName = itemView.findViewById(R.id.professionalName);
            professionalRating = itemView.findViewById(R.id.professionalRating);
        }
    }
}