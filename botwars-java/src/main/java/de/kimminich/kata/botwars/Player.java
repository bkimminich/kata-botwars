package de.kimminich.kata.botwars;

public class Player {

    private final Bot[] team;

    public Player(Bot... team) {
        this.team = team;
    }

    public Bot[] getTeam() {
        return team;
    }
}
