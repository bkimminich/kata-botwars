package de.kimminich.kata.botwars.builders;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.ui.UserInteraction;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;

public final class PlayerBuilder {

    private Bot[] team = {anyBot(), anyBot(), anyBot()};
    private UserInteraction ui = bots -> {
        if (bots != null && bots.size() != 0) {
            return bots.get(0);
        } else {
            return null;
        }
    };

    private PlayerBuilder() {
    }

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public PlayerBuilder withUI(UserInteraction ui) {
        this.ui = ui;
        return this;
    }

    public PlayerBuilder withTeam(Bot... team) {
        this.team = team;
        return this;
    }

    public Player build() {
        return new Player(ui, team);
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }
}
