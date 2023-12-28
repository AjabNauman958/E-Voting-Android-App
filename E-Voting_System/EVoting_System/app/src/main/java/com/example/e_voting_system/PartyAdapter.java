package com.example.e_voting_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

    private List<Party> partyList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private Context context;

    public PartyAdapter(List<Party> partyList, Context context) {
        this.partyList = partyList;
        this.context = context;
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.party_item, parent, false);
        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
        Party party = partyList.get(position);

        // Bind data to views
        holder.bind(party, position);
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButtonParty;
        ImageView imageViewParty;
        TextView textViewPartyName;

        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButtonParty = itemView.findViewById(R.id.radioButtonParty);
            imageViewParty = itemView.findViewById(R.id.imageViewParty);
            textViewPartyName = itemView.findViewById(R.id.textViewPartyName);

            // Move the OnClickListener here
            radioButtonParty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the selected position and notify data set changed
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

        public void bind(Party party, int position) {
            // Bind data to views
            radioButtonParty.setChecked(position == selectedPosition);
            imageViewParty.setImageResource(party.getLogo());
            textViewPartyName.setText(party.getName());
        }
    }

    public Party getSelectedParty() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return partyList.get(selectedPosition);
        }
        return null;
    }
}
