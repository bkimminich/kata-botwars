package de.kimminich.kata.botwars;

import static de.kimminich.kata.botwars.BotBuilder.aBot;

public final class PlayerBuilder {

    private Bot[] team = {aBot().build(), aBot().build(), aBot().build()};

    private PlayerBuilder() {
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = team;
        return this;
    }

    public Player build() {
        return new Player(team);
    }
}
