package com.dev.nick.scorch.tournaments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.model.Player;
import com.dev.nick.scorch.model.Team;
import com.dev.nick.scorch.players.PlayerListAdapter;
import com.dev.nick.scorch.teams.TeamListAdapter;

import java.util.List;

public class TournamentSelectMembers extends Fragment implements View.OnClickListener {

    private RecyclerView mPlayerRecyclerView;
    private RecyclerView mTeamRecyclerView;
    private PlayerListAdapter mPlayerAdapter;
    private TeamListAdapter mTeamAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mTeamLayoutManager;

    private Button btnBack;
    private Button btnStart;

    private OnFragmentInteractionListener mListener;

    public static TournamentSelectMembers newInstance() {
        TournamentSelectMembers fragment = new TournamentSelectMembers();
        return fragment;
    }

    public TournamentSelectMembers() {
        // Required empty public constructor
    }

    public void changeAdapter(int type) {
        switch(type) {
            case 0:
                mPlayerRecyclerView.setVisibility(View.VISIBLE);
                mTeamRecyclerView.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mPlayerRecyclerView.setVisibility(View.INVISIBLE);
                mTeamRecyclerView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if( mListener != null &&
            mPlayerRecyclerView != null) {
            changeAdapter(mListener.getType());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tournament_select_members_fragment, container, false);

        List<Player> lstPlayers = Player.listAll(Player.class);
        mPlayerAdapter = new PlayerListAdapter(getContext(), lstPlayers);

        List<Team> lstTeams = Team.listAll(Team.class);
        mTeamAdapter = new TeamListAdapter(getActivity(), lstTeams);

        // Set the adapter
        mPlayerRecyclerView = (RecyclerView) v.findViewById(R.id.playerList);
        mPlayerRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPlayerRecyclerView.setLayoutManager(mLayoutManager);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);
        mPlayerRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        long playerid = mPlayerAdapter.getItemId(position);
                        ImageView check = (ImageView) view.findViewById(R.id.selected);
                        if (check != null) {
                            String pid = Long.toString(playerid);
                            if (check.getVisibility() == View.VISIBLE) {
                                check.setVisibility(View.INVISIBLE);
                                mListener.onMemberSelected(pid);
                            } else if (check.getVisibility() == View.INVISIBLE) {
                                check.setVisibility(View.VISIBLE);
                                mListener.onMemberSelected(pid);
                            }
                        }
                    }
                })
        );

        // Set the adapter
        mTeamRecyclerView = (RecyclerView) v.findViewById(R.id.teamList);
        mTeamRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mTeamLayoutManager = new LinearLayoutManager(getActivity());
        mTeamRecyclerView.setLayoutManager(mTeamLayoutManager);
        mTeamRecyclerView.setAdapter(mTeamAdapter);
        mTeamRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        long teamid = mTeamAdapter.getItemId(position);
                        ImageView check = (ImageView) view.findViewById(R.id.selected);
                        if (check != null) {
                            String pid = Long.toString(teamid);
                            if (check.getVisibility() == View.VISIBLE) {
                                check.setVisibility(View.INVISIBLE);
                                mListener.onMemberSelected(pid);
                            } else if (check.getVisibility() == View.INVISIBLE) {
                                check.setVisibility(View.VISIBLE);
                                mListener.onMemberSelected(pid);
                            }
                        }
                    }
                })
        );

        btnBack = (Button) v.findViewById(R.id.cancelBtn);
        btnStart = (Button) v.findViewById(R.id.nextBtn);

        btnBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof Activity)
                mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getName()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.cancelBtn:
                if(mListener != null) {
                    mListener.onBack(1);
                }
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.onCreateTourney();
                }
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onCreateTourney();
        void onMemberSelected(String id);
        void onBack(int item);
        int getType();
    }

}
