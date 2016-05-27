package de.kimminich.kata.botwars;

import static de.kimminich.kata.botwars.BotBuilder.anyBot;

public final class PlayerBuilder {

    private Bot[] team = {anyBot(), anyBot(), anyBot()};

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
