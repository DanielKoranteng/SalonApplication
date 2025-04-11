package com.example.salonapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ProfessionalViewHolder> {

    private final List<Professional> professionalList;
    private final SelectProfessionalActivity listener;

    public ProfessionalAdapter(List<Professional> professionalList, SelectProfessionalActivity listener) {
        this.professionalList = professionalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfessionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_professional, parent, false);
        return new ProfessionalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalViewHolder holder, int position) {
        Professional professional = professionalList.get(position);
        holder.bind(professional);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProfessionalSelected(professional);
            }
        });
    }

    @Override
    public int getItemCount() {
        return professionalList.size();
    }

    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profileImage;
        private final TextView professionalName;
        private final TextView professionalRating;
        private final TextView specialization;
        private final TextView experience;

        public ProfessionalViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            professionalName = itemView.findViewById(R.id.professionalName);
            professionalRating = itemView.findViewById(R.id.professionalRating);
            specialization = itemView.findViewById(R.id.specialization);
            experience = itemView.findViewById(R.id.experience);
        }

        void bind(Professional professional) {
            professionalName.setText(professional.getName());
            professionalRating.setText(professional.getRating());
            specialization.setText(professional.getSpecialization());
            experience.setText(professional.getExperience());
            
            Glide.with(itemView.getContext())
                    .load(professional.getImageUrl())
                    .circleCrop()
                    .placeholder(R.drawable.default_profile)
                    .into(profileImage);
        }
    }
}