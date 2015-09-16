package com.dev.nick.scorch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nick on 9/12/2015.
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    // TODO: need cursor of player data

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView textName;
        TextView textJoined;
        ImageView imageIcon;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.player_name);
            textJoined = (TextView) itemView.findViewById(R.id.player_joined);
            imageIcon = (ImageView) itemView.findViewById(R.id.player_icon);
        }

    }

    PlayerListAdapter(){
        //TODO: set cursor with player data
    }

    @Override
    public PlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(v,viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayerListAdapter.ViewHolder holder, int position) {
        holder.textName.setText("Ulysses S. Butterbee");
        holder.textJoined.setText("July 5, 1990");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
