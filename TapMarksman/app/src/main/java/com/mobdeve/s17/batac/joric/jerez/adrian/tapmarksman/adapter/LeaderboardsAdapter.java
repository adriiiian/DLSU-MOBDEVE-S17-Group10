package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public LeaderboardsAdapter.LeaderboardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_data, parent, false);

        LeaderboardsViewHolder leaderboardsViewHolder = new LeaderboardsViewHolder(view);

        return leaderboardsViewHolder;
    }

    @Override
    public void onBindViewHolder(LeaderboardsAdapter.LeaderboardsViewHolder holder, int position) {
        holder.tv_username.setText(userArrayList.get(position).getUserName());
        holder.tv_score.setText(Integer.toString(userArrayList.get(position).getHighestScore()));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class LeaderboardsViewHolder extends RecyclerView.ViewHolder{
        TextView tv_username;
        TextView tv_score;
        public LeaderboardsViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username_leaderboards);
            tv_score = itemView.findViewById(R.id.tv_score_leaderboards);
        }
    }
}
