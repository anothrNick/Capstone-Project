package com.dev.nick.scorch.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by Nick on 11/15/2015.
 */
public class Game extends SugarRecord<Game> {
    public boolean complete;
    public String created;

    @Ignore
    public List<GameTeam> gameTeamList;

    public Game() {

    }

    public void loadTeamList() {
        if(getId() > 0) {
            gameTeamList = GameTeam.find(GameTeam.class, "game=?", Long.toString(getId()));
        }
    }
}
