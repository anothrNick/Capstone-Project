package com.dev.nick.scorch.teams;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.model.Player;
import com.dev.nick.scorch.model.Team;
import com.dev.nick.scorch.model.TeamPlayer;
import com.dev.nick.scorch.players.PlayerImageListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 9/15/2015.
 *
 * Adapter for team list...
 */
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    private Context mContext;
    private List<Team> teamList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView teamName;

        public ViewHolder(View itemView) {
            super(itemView);
            teamName = (TextView)itemView.findViewById(R.id.team_name);
        }
    }

    public TeamListAdapter(Context context, List<Team> teamList){
        this.mContext = context;
        this.teamList = teamList;
    }

    public void resetTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.team_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.teamName.setText(teamList.get(position).name);
    }

    @Override
    public int getItemCount() {
        if(teamList != null)
            return teamList.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return teamList.get(position).getId();
    }
}
