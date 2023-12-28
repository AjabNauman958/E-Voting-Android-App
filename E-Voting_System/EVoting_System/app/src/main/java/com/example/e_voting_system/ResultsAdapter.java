package com.example.e_voting_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    private List<Result> resultList;
    private Context context;

    public ResultsAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_item, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        Result result = resultList.get(position);

        // Bind data to views
        holder.textViewPartyName.setText(result.getPartyName());
        holder.textViewVotes.setText(String.valueOf(result.getVotes()));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void setResults(List<Result> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPartyName;
        TextView textViewVotes;

        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPartyName = itemView.findViewById(R.id.textViewPartyName);
            textViewVotes = itemView.findViewById(R.id.textViewVotes);
        }
    }
}
