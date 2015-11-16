package com.dev.nick.scorch.model;

import com.orm.SugarRecord;

/**
 * Created by Nick on 11/15/2015.
 */
public class TeamPlayer extends SugarRecord<TeamPlayer> {
    public Team team;
    public Player player;

    public TeamPlayer() {

    }
}
