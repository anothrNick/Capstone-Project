package com.dev.nick.scorch.model;

import java.util.ArrayList;

/**
 * Created by Nick on 11/8/2015.
 */
public class Tournament {
    private final int MIN = 3;
    private final int MAX = 32;
    private String lastError = "";
    private ArrayList<TournamentGame> games;

    public Tournament() {
        games = new ArrayList<>();

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

            // create round 1 with actual participants
            for(int i = 0; i < participants.size(); i++) {
                if(participants.size() <= i) {
                    String p1 = participants.get(i);
                    String p2 = "bye";

                    if(participants.size() > i)
                        p2 = participants.get(i + 1);

                    games.add(new TournamentGame(1, new String[]{p1, p2}));

                    i++;
                }
            }

            boolean bDone = false;
            int round = 2;

            while(!bDone) {
                ArrayList<TournamentGame> list = this.getGamesForRound(round - 1);



                round++;
            }

            return true;
        }
    }

    public ArrayList<TournamentGame> getGamesForRound(int round) {
        ArrayList<TournamentGame> list = new ArrayList<>();

        for(TournamentGame game : games) {
            if(round == game.getRound())
                list.add(game);
        }

        return list;
    }

    public class TournamentGame {
        private ArrayList<String> participants;
        private int id;
        private int round;
        private int nextGame;

        public TournamentGame(int round, String[] ps) {
            this.id = -1;
            this.round = round;
            this.participants = new ArrayList<>();

            for(int i = 0; i < ps.length; i++) {
                participants.add(ps[i]);
            }
        }

        public void addParticipant(String id) {
            participants.add(id);
        }

        // GETTERS
        public int getRound() { return this.round; }

        // SETTERS
        public void setNextGame(int nextGame) { this.nextGame = nextGame; }
    }
}
