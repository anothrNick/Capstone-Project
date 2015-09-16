package com.dev.nick.scorch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nick on 9/15/2015.
 */
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView teamName;

        public ViewHolder(View itemView) {
            super(itemView);
            teamName = (TextView)itemView.findViewById(R.id.team_name);
        }
    }

    public TeamListAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.team_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.teamName.setText("A Team Name");
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
