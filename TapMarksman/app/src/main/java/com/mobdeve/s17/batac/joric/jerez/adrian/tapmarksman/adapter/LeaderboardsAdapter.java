package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.R;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardsViewHolder> {

    private ArrayList<User> userArrayList;
    private Context context;

    public LeaderboardsAdapter(Context context, ArrayList<User> userArrayList){
        this.userArrayList = userArrayList;
        this.context = context;
    }

    /**
     * Creates view for leaderboard
     */
    @Override
    public LeaderboardsAdapter.LeaderboardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_data, parent, false);

        LeaderboardsViewHolder leaderboardsViewHolder = new LeaderboardsViewHolder(view);

        return leaderboardsViewHolder;
    }

    /**
     * Sets the leaderboard based on the scores of each user
     * color is also alternate
     */
    @Override
    public void onBindViewHolder(LeaderboardsAdapter.LeaderboardsViewHolder holder, int position) {
        holder.tv_username.setText(userArrayList.get(position).getUserName());
        holder.tv_score.setText(Integer.toString(userArrayList.get(position).getHighestScore()));
        holder.tv_rank.setText(Integer.toString(position + 1));
        if((position + 1) % 2 == 1){
            holder.tv_username.setBackgroundResource(R.color.lightgreen);
            holder.tv_score.setBackgroundResource(R.color.lightgreen);
            holder.tv_rank.setBackgroundResource(R.color.lightgreen);
        }
        else{
            holder.tv_username.setBackgroundResource(R.color.darkgreenbtns);
            holder.tv_score.setBackgroundResource(R.color.darkgreenbtns);
            holder.tv_rank.setBackgroundResource(R.color.darkgreenbtns);
//            holder.cl_main.setBackgroundResource(R.color.darkgreenbtns);
        }
    }

    /**
     * Gets size of user arraylist
     */
    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    /**
     * Sets the content for the leaderboards
     */
    public class LeaderboardsViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout cl_main;
        TextView tv_username;
        TextView tv_score;
        TextView tv_rank;
        public LeaderboardsViewHolder(View itemView) {
            super(itemView);
            cl_main = itemView.findViewById(R.id.cl_main);
            tv_username = itemView.findViewById(R.id.tv_username_leaderboards);
            tv_score = itemView.findViewById(R.id.tv_score_leaderboards);
            tv_rank = itemView.findViewById(R.id.tv_rank_leaderboards);
        }
    }
}
