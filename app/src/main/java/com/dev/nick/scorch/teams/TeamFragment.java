package com.dev.nick.scorch.teams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;
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
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        //}
        bStarted = false;

        List<Team> lstTeams = Team.listAll(Team.class);

        mAdapter = new TeamListAdapter(getActivity(), lstTeams);

        //db.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.team_fragment, container, false);

        // Set the adapter
        mRecyclerView = (RecyclerView) v.findViewById(R.id.team_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
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
            List<Team> lstTeams = Team.listAll(Team.class);
            mAdapter.resetTeamList(lstTeams);
            mAdapter.notifyDataSetChanged();
        }
    }
}
