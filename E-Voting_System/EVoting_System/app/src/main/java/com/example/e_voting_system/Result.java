package com.example.e_voting_system;

public class Result {
    private String partyName;
    private int votes;

    public Result(String partyName, int votes) {
        this.partyName = partyName;
        this.votes = votes;
    }

    public String getPartyName() {
        return partyName;
    }

    public int getVotes() {
        return votes;
    }
}
