package de.kimminich.kata.botwars;

import java.util.List;

public class Player {

    private String name;
    private List<Bot> team;

    public Player(String name, List<Bot> team) {
        this.name = name;
        this.team = team;
    }

    public List<Bot> getTeam() {
        return team;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
