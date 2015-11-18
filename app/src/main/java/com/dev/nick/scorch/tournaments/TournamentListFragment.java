package com.dev.nick.scorch.tournaments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;

public class TournamentListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TournamentListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton newTourneyBtn;

    public static TournamentListFragment newInstance() {
        return new TournamentListFragment();
    }

    public TournamentListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tournament_list_fragment, container, false);

//        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mRecyclerView.setAdapter(mAdapter);
//
//        newTourneyBtn = (FloatingActionButton) v.findViewById(R.id.newTourney);
//        newTourneyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), TournamentNewActivity.class);
//                startActivity(intent);
//            }
//        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.TOURNAMENTS);
    }
}
