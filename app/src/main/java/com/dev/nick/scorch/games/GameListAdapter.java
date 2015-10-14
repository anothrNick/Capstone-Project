package com.dev.nick.scorch.games;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Nick on 9/13/2015.
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    ArrayList<GameBean> games;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamOne;
        TextView teamTwo;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            teamOne = (TextView) itemView.findViewById(R.id.team_one);
            teamTwo = (TextView) itemView.findViewById(R.id.team_two);
        }

    }

    GameListAdapter(ArrayList<GameBean> games){
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
        holder.teamOne.setText(games.get(position).teamOne);
        holder.teamTwo.setText(games.get(position).teamTwo);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}