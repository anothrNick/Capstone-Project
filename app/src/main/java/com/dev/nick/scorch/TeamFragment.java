package com.dev.nick.scorch;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TeamFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.team_fragment, container, false);

        mAdapter = new TeamListAdapter();

        // Set the adapter
        mRecyclerView = (RecyclerView) v.findViewById(R.id.team_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.TEAMS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
