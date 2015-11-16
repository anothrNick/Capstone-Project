package com.dev.nick.scorch.model;

import com.orm.SugarRecord;

/**
 * Created by Nick on 11/15/2015.
 */
public class Team extends SugarRecord<Team>{

    public String created, name, avatar;

    public Team() {

    }
}
