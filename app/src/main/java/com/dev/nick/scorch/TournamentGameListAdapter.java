package com.dev.nick.scorch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nick on 9/15/2015.
 */
public class TournamentGameListAdapter extends RecyclerView.Adapter<TournamentGameListAdapter.ViewHolder> {

    private int count = 0;
    // TODO: need cursor of player data

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView teamOne;
        TextView teamTwo;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            teamOne = (TextView) itemView.findViewById(R.id.team_one);
            teamTwo = (TextView) itemView.findViewById(R.id.team_two);
        }

    }

    TournamentGameListAdapter(int count){
        //TODO: set cursor with game data
        this.count = count;
    }

    @Override
    public TournamentGameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_game_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(v,viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TournamentGameListAdapter.ViewHolder holder, int position) {
        //holder.teamOne.setText("Ulysses S. Butterbee");
        //holder.teamTwo.setText("Baba Booey");
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}