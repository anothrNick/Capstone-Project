package com.dev.nick.scorch.teams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.model.Team;

import java.util.List;

public class TeamFragment extends Fragment {

    public static String TAG = TeamFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TeamListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean bStarted;

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    public TeamFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bStarted = false;

        List<Team> lstTeams = Team.listAll(Team.class);

        mAdapter = new TeamListAdapter(getActivity(), lstTeams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.team_fragment, container, false);

        // Set the adapter
        mRecyclerView = (RecyclerView) v.findViewById(R.id.team_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton teamBtn = (FloatingActionButton) v.findViewById(R.id.newTeam);
        teamBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamNewActivity.class);
                startActivity(intent);
            }
        });

        bStarted = true;

        if(mAdapter.getItemCount() <= 0) {
            TextView empty = (TextView) v.findViewById(R.id.teams_empty);
            empty.setVisibility(View.VISIBLE);
        }

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //long playerid = mAdapter.getItemId(position);
                        //Intent intent = new Intent(getActivity(), );
                        //startActivity(intent);
                    }
                })
        );

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.TEAMS);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(bStarted) {
            reloadTeams();

            ((MainActivity) getActivity()).onSectionAttached(MainActivity.TEAMS);
        }
    }

    public void reloadTeams() {
        if(mAdapter != null) {
            mAdapter.resetTeamList(Team.listAll(Team.class));
            mAdapter.notifyDataSetChanged();
        }
    }
}
