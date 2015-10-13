package com.dev.nick.scorch.dao;

import android.provider.BaseColumns;

/**
 * Created by Nick on 9/10/2015.
 */
public final class ScorchContract {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "scorch.db";

    public static final String TYPE_TEXT = " TEXT";
    public static final String COMMA_SEP = ",";

    public ScorchContract(){}

    /**
     * Players table
     */
    public static abstract class Players implements BaseColumns {
        public static final String TABLE_NAME = "players";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_AVATAR = "avatar";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_RANK + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_CREATED + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_AVATAR + ScorchContract.TYPE_TEXT +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Teams table
     */
    public static abstract class Teams implements BaseColumns {
        public static final String TABLE_NAME = "teams";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AVATAR = "avatar";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_CREATED + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_AVATAR + ScorchContract.TYPE_TEXT +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * TeamPlayers table
     */
    public static abstract class TeamPlayers implements BaseColumns {
        public static final String TABLE_NAME = "teamplayers";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TEAM = "teamid";
        public static final String COLUMN_PLAYER = "playerid";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TEAM + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_PLAYER + ScorchContract.TYPE_TEXT +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Game table
     */
    public static abstract class Game implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CREATED = "created";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CREATED + ScorchContract.TYPE_TEXT +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * GameTeams table
     */
    public static abstract class GameTeams implements BaseColumns {
        public static final String TABLE_NAME = "gameteams";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TEAM = "teamid";
        public static final String COLUMN_GAME = "gameid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_SCORE = "score";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TEAM + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_GAME + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_TYPE + ScorchContract.TYPE_TEXT + ScorchContract.COMMA_SEP +
                COLUMN_SCORE + ScorchContract.TYPE_TEXT +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
