package com.dev.nick.scorch.games;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;
import com.dev.nick.scorch.model.Game;
import com.dev.nick.scorch.model.Games;
import com.dev.nick.scorch.players.PlayerDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 10/20/2015.
 */
public class GameListFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView mRecyclerView;
    private GameListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Game> games;

    public GameListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            long id;
            int itype;

            itype = extras.getInt(GameFragment.GAME_TYPE);

            if(itype == 1) {
                id = extras.getLong(PlayerDetailActivity.PLAYER_ID);
                games = Game.listAll(Game.class);
            }
            else {
                games = Game.listAll(Game.class);
            }
        }
        else {
            games = Game.listAll(Game.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_tab_content,
                container, false);

        mAdapter = new GameListAdapter(games);

        // Set the adapter
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.game_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        long gameid = mAdapter.getItemId(position);
                        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                        intent.putExtra(GameDetailActivity.GAME_ID, gameid);
                        getActivity().startActivity(intent);
                    }
                })
        );

        return rootView;
    }
}
