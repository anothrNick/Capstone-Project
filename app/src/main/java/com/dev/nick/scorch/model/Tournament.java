package com.dev.nick.scorch.model;

import java.util.ArrayList;

/**
 * Created by Nick on 11/8/2015.
 */
public class Tournament {
    private final int MIN = 3;
    private final int MAX = 32;
    private String lastError = "";

    public Tournament() {
        // require even amount of players for v1

        // For each 2 members, create game

        // Get round count
    }

    public String getLastError() {
        return lastError;
    }

    public boolean createBracket(ArrayList<String> participants) {
        if(participants.size() < MIN) {
            lastError = "Tournaments require more than 3 players";
            return false;
        }
        else if(participants.size() > MAX) {
            lastError = "Tournaments only support up to 32 players";
            return false;
        }
        else {

            for(int i = 0; i < participants.size(); i++) {

            }
            return true;
        }
    }

    public class TournamentGame {
        private ArrayList<String> participants;
        private int round;
        private int nextGame;

        public TournamentGame(int round) {
            this.round = round;
            this.participants = new ArrayList<>();
        }

        public void addParticipant(String id) {
            participants.add(id);
        }
    }
}
