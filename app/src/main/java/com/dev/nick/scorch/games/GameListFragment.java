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
import com.dev.nick.scorch.players.PlayerDetailActivity;

import java.util.ArrayList;

/**
 * Created by Nick on 10/20/2015.
 */
public class GameListFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView mRecyclerView;
    private GameListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScorchDbHelper dbHelper;
    private ArrayList<GameBean> games;

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

            if(itype == 0) {
                id = extras.getLong(PlayerDetailActivity.PLAYER_ID);
            }
        }

        games = new ArrayList<>();
        dbHelper = new ScorchDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor gameCursor = db.query(
                ScorchContract.Game.TABLE_NAME,
                ScorchContract.Game.projection,
                null,
                null,
                null,
                null,
                ScorchContract.Game.sortOrder
        );
        Cursor gameTeamsCursor;
        Cursor teamCursor;

        if(gameCursor.moveToFirst()) {
            do {
                GameBean gameBean = new GameBean();
                gameBean.id = gameCursor.getLong(gameCursor.getColumnIndex(ScorchContract.Game.COLUMN_ID));

                String where = ScorchContract.GameTeams.COLUMN_GAME + " = ?";
                String[] whereArgs = new String[]{
                        Long.toString(gameBean.id)
                };
                gameTeamsCursor = db.query(
                        ScorchContract.GameTeams.TABLE_NAME,
                        ScorchContract.GameTeams.projection,
                        where,
                        whereArgs,
                        null,
                        null,
                        ScorchContract.GameTeams.sortOrder
                );

                int type = -1;
                String TABLE_NAME = "";
                String COLUMN_NAME = "";
                String sortOrder = "";
                String[] projection = {};
                if(gameTeamsCursor.moveToFirst()) {
                    type = gameTeamsCursor.getInt(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_TYPE));
                    gameBean.type = type;
                    gameBean.teamOneScore = gameTeamsCursor.getInt(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_SCORE));
                    gameBean.teamOneId = gameTeamsCursor.getLong(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_ID));

                    where = ScorchContract.Teams.COLUMN_ID + " = ?";
                    whereArgs = new String[]{
                            gameTeamsCursor.getString(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_TEAM))
                    };

                    if(type == 0) {
                        TABLE_NAME = ScorchContract.Players.TABLE_NAME;
                        COLUMN_NAME = ScorchContract.Players.COLUMN_NAME;
                        sortOrder = ScorchContract.Players.sortOrder;
                        projection = ScorchContract.Players.projection;
                    }
                    else if(type == 1) {
                        TABLE_NAME = ScorchContract.Teams.TABLE_NAME;
                        COLUMN_NAME = ScorchContract.Teams.COLUMN_NAME;
                        sortOrder = ScorchContract.Teams.sortOrder;
                        projection = ScorchContract.Teams.projection;
                    }

                    if(type > -1) {
                        teamCursor = db.query(
                                TABLE_NAME,
                                projection,
                                where,
                                whereArgs,
                                null,
                                null,
                                sortOrder
                        );
                        if(teamCursor.moveToFirst()) {
                            gameBean.teamOne = teamCursor.getString(teamCursor.getColumnIndex(COLUMN_NAME));
                        }
                    }
                }
                if(gameTeamsCursor.moveToNext()) {
                    gameBean.teamTwoScore = gameTeamsCursor.getInt(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_SCORE));
                    gameBean.teamTwoId = gameTeamsCursor.getLong(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_ID));

                    whereArgs = new String[]{
                            gameTeamsCursor.getString(gameTeamsCursor.getColumnIndex(ScorchContract.GameTeams.COLUMN_TEAM))
                    };

                    if(type > -1) {
                        teamCursor = db.query(
                                TABLE_NAME,
                                projection,
                                where,
                                whereArgs,
                                null,
                                null,
                                sortOrder
                        );
                        if(teamCursor.moveToFirst()) {
                            gameBean.teamTwo = teamCursor.getString(teamCursor.getColumnIndex(COLUMN_NAME));
                        }
                    }
                }

                games.add(gameBean);
            }while(gameCursor.moveToNext());
        }
        db.close();
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
                        GameBean game = mAdapter.getGame(position);
                        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                        intent.putExtra(GameDetailActivity.GAME_ID, gameid);
                        intent.putExtra(GameDetailActivity.GAME, game);
                        getActivity().startActivity(intent);
                    }
                })
        );

        return rootView;
}
}
