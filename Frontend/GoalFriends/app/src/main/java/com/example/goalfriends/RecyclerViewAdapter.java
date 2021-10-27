package com.example.goalfriends;

import.android.support.annotation.NonNull;
//add widget recyclerView
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_GOAL = 1;
    public List<Goals> goalsList;
    public RecyclerViewAdapter(List<Goals> gList){
        goalsList = gList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_GOAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_row, parent, false);
            return new ItemViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_loading, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(viewHolder instanceof ItemViewHolder) {
            populateGoalRows((ItemViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return goalsList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_GOAL;
    }

    @Override
    public int getItemCount() {
        return goalsList == null ? 0 : goalsList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvGoal;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            tvGoal = itemView.findViewById(R.id.tvGoal);
        }

        private class LoadingHolder extends RecyclerView.ViewHolder{
            public LoadingHolder(@NonNull View goalView){
                super(goalView);
            }
        }

        private void populateGoalRows(ItemViewHolder viewHolder, int position){
            Goal goal = goalsList.get(position);
            viewHolder.tvGoal.setText(goal);
        }
    }
}
