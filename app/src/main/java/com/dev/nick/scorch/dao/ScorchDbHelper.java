package com.dev.nick.scorch.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nick on 9/10/2015.
 */
public class ScorchDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = ScorchContract.DATABASE_VERSION;
    public static final String DATABASE_NAME = ScorchContract.DATABASE_NAME;

    public ScorchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(ScorchContract.Players.CREATE_TABLE);
        db.execSQL(ScorchContract.Teams.CREATE_TABLE);
        db.execSQL(ScorchContract.TeamPlayers.CREATE_TABLE);
        db.execSQL(ScorchContract.Game.CREATE_TABLE);
        db.execSQL(ScorchContract.GameTeams.CREATE_TABLE);
        db.execSQL(ScorchContract.Tournaments.CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(ScorchContract.Players.DELETE_TABLE);
        db.execSQL(ScorchContract.Teams.DELETE_TABLE);
        db.execSQL(ScorchContract.TeamPlayers.DELETE_TABLE);
        db.execSQL(ScorchContract.Game.DELETE_TABLE);
        db.execSQL(ScorchContract.GameTeams.DELETE_TABLE);
        db.execSQL(ScorchContract.Tournaments.DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
