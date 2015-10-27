package com.dev.nick.scorch.games;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;
import com.dev.nick.scorch.players.PlayerFragment;
import com.dev.nick.scorch.players.PlayerListAdapter;
import com.dev.nick.scorch.teams.TeamFragment;
import com.dev.nick.scorch.teams.TeamListAdapter;

public class GameSelectMembersFragment extends Fragment implements View.OnClickListener {

    private ScorchDbHelper dbHelper;
    private RecyclerView mPlayerRecyclerView;
    private RecyclerView mTeamRecyclerView;
    private PlayerListAdapter mPlayerAdapter;
    private TeamListAdapter mTeamAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mTeamLayoutManager;

    private Button btnBack;
    private Button btnStart;

    private OnFragmentInteractionListener mListener;

    public static GameSelectMembersFragment newInstance() {
        GameSelectMembersFragment fragment = new GameSelectMembersFragment();
        return fragment;
    }

    public GameSelectMembersFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_select_members_fragment, container, false);

        //players = new ArrayList<>();
        dbHelper = new ScorchDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor playerCursor = db.query(
                ScorchContract.Players.TABLE_NAME,
                ScorchContract.Players.projection,
                null,
                null,
                null,
                null,
                ScorchContract.Players.sortOrder
        );
        mPlayerAdapter = new PlayerListAdapter(getActivity(), playerCursor);

        Cursor teamCursor = db.query(
                ScorchContract.Teams.TABLE_NAME,
                ScorchContract.Teams.projection,
                null,
                null,
                null,
                null,
                ScorchContract.Teams.sortOrder
        );
        mTeamAdapter = new TeamListAdapter(getActivity(), teamCursor);

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
                            //myButton.setAnimation(animFadeOut)
                            if (check.getVisibility() == View.VISIBLE) {
                                check.setVisibility(View.INVISIBLE);
                                mListener.onMemberSelected(pid);
                            } else if (check.getVisibility() == View.INVISIBLE) {
                                if(((GameNewActivity)getActivity()).memberCount() < 2) {
                                    check.setVisibility(View.VISIBLE);
                                    mListener.onMemberSelected(pid);
                                }
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
                            //myButton.setAnimation(animFadeOut)
                            if (check.getVisibility() == View.VISIBLE) {
                                check.setVisibility(View.INVISIBLE);
                                mListener.onMemberSelected(pid);
                            } else if (check.getVisibility() == View.INVISIBLE) {
                                if(((GameNewActivity)getActivity()).memberCount() < 2) {
                                    check.setVisibility(View.VISIBLE);
                                    mListener.onMemberSelected(pid);
                                }
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
        Log.d("GameSelectType", "something pressed");
        switch (id) {
            case R.id.cancelBtn:
                if(mListener != null) {
                    mListener.onBack();
                }
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.onStartGame();
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
        void onStartGame();
        void onMemberSelected(String id);
        void onBack();
    }

}
