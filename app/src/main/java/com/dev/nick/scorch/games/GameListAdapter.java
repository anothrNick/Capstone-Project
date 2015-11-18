package com.dev.nick.scorch.games;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.model.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 9/13/2015.
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    List<Game> games;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamOne;
        TextView teamTwo;
        TextView teamOneScore;
        TextView teamTwoScore;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            teamOne = (TextView) itemView.findViewById(R.id.team_one);
            teamTwo = (TextView) itemView.findViewById(R.id.team_two);
            teamOneScore = (TextView) itemView.findViewById(R.id.team_one_score);
            teamTwoScore = (TextView) itemView.findViewById(R.id.team_two_score);
        }

    }

    GameListAdapter(List<Game> games){
        this.games = games;
    }

    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(v,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameListAdapter.ViewHolder holder, int position) {
        if(games.get(position).gameTeamList == null || games.get(position).gameTeamList.size() <= 0) {
            games.get(position).loadTeamList();
        }

        if(games.get(position).gameTeamList.get(0).type == Game.PLAYERS) {
            holder.teamOne.setText(games.get(position).gameTeamList.get(0).player.name);
            holder.teamTwo.setText(games.get(position).gameTeamList.get(1).player.name);
        }
        else {
            holder.teamOne.setText(games.get(position).gameTeamList.get(0).team.name);
            holder.teamTwo.setText(games.get(position).gameTeamList.get(1).team.name);
        }

        holder.teamOneScore.setText(Integer.toString(games.get(position).gameTeamList.get(0).score));
        holder.teamTwoScore.setText(Integer.toString(games.get(position).gameTeamList.get(1).score));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return games.get(position).getId();
    }

    public Game getGame(int position) {
        return games.get(position);
    }
}