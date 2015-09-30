package com.dev.nick.scorch.teams;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

public class TeamFragment extends Fragment {

    private ScorchDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private TeamListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean bStarted;

    public static String[] team_projection = {
            ScorchContract.Teams.COLUMN_NAME,
            ScorchContract.Teams.COLUMN_CREATED,
            ScorchContract.Teams.COLUMN_ID
    };

    public static String team_sortOrder = ScorchContract.Teams.COLUMN_CREATED + " DESC";

    public static TeamFragment newInstance() {
        TeamFragment fragment = new TeamFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        bStarted = false;

        dbHelper = new ScorchDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                ScorchContract.Teams.TABLE_NAME,
                team_projection,
                null,
                null,
                null,
                null,
                team_sortOrder
        );

        mAdapter = new TeamListAdapter(getActivity(), cursor);

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
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.TEAMS);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(bStarted)
            reloadTeams();
    }

    public void reloadTeams() {

        if(mAdapter != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    ScorchContract.Teams.TABLE_NAME,
                    team_projection,
                    null,
                    null,
                    null,
                    null,
                    team_sortOrder
            );
            mAdapter.changeCursor(cursor);

            //db.close();
        }

    }
}
