package com.dev.nick.scorch.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;
import com.dev.nick.scorch.games.GameBean;

import java.util.ArrayList;

/**
 * Created by Nick on 10/20/2015.
 */
public class Games {

    public static ArrayList<GameBean> getAllGames(ScorchDbHelper dbHelper) {
        ArrayList<GameBean> games = new ArrayList<>();

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

        return games;
    }

    public static ArrayList<GameBean> getPlayersGames(long playerid, ScorchDbHelper dbHelper) {
        ArrayList<GameBean> games = new ArrayList<>();

        return games;
    }
}
