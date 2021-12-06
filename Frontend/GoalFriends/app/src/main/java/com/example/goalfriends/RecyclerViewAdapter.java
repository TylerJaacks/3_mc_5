package com.example.goalfriends;

import androidx.annotation.NonNull;
//add widget recyclerView
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.goalfriends.goals.Goal;
import com.example.goalfriends.goals.GoalUtil;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_GOAL = 1;
    public List<Goal> goalsList;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, List<Goal> gList){
        this.mInflater = LayoutInflater.from(context);
        this.goalsList = gList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_GOAL){
            View view = mInflater.inflate(R.layout.goal_row, parent, false);
            return new ItemViewHolder(view);
        }else {

            View view = mInflater.inflate(R.layout.goal_loading, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).populateGoalRows((ItemViewHolder) holder, position);
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

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvGoal;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGoal = itemView.findViewById(R.id.tvGoal);
        }

        private void populateGoalRows(ItemViewHolder viewHolder, int position){
            Goal goalX = goalsList.get(position);
            viewHolder.tvGoal.setText(GoalUtil.DisplayGoal(goalX));
        }


    }

    private class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(@NonNull View view) {
            super(view);
        }
    }
}
