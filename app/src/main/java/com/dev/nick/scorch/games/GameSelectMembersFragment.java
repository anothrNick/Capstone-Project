package com.dev.nick.scorch.games;

import android.app.Activity;
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

public class GameSelectMembersFragment extends Fragment implements View.OnClickListener {

    private ScorchDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private PlayerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        Cursor cursor = db.query(
                ScorchContract.Players.TABLE_NAME,
                PlayerFragment.player_projection,
                null,
                null,
                null,
                null,
                PlayerFragment.player_sortOrder
        );
        mAdapter = new PlayerListAdapter(getActivity(), cursor);

        // Set the adapter
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        long playerid = mAdapter.getItemId(position);
                        ImageView check = (ImageView) view.findViewById(R.id.selected);
                        if (check != null) {
                            String pid = Long.toString(playerid);
                            //myButton.setAnimation(animFadeOut)
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
