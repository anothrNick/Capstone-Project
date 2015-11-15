package com.dev.nick.scorch.model;

import com.orm.SugarRecord;

/**
 * Created by Nick on 11/14/2015.
 */
public class Player extends SugarRecord<Player> {
    public String name, rank, created, avatar;

    public Player() {

    }

    public Player(String name, String created) {
        this.name = name;
        this.created = created;
    }
}
