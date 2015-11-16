package com.dev.nick.scorch.model;

import com.orm.SugarRecord;

/**
 * Created by Nick on 11/15/2015.
 */
public class GameTeam extends SugarRecord<GameTeam> {
    public Team team;
    public Player player;
    public Game game;
    public int type;
    public int score;

    public GameTeam() {

    }
}
