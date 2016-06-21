package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;

public final class PlayerBuilder {

    private static int id = 0;

    private List<Bot> team = new ArrayList<>(Arrays.asList(anyBot(), anyBot(), anyBot()));
    private String name = "Player " + ++id;

    private PlayerBuilder() {
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = new ArrayList<>();
        Collections.addAll(this.team, team);
        return this;
    }

    public PlayerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Player build() {
        return new Player(name, team);
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }

}
