package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.UserInteraction;

import static de.kimminich.kata.botwars.BotBuilder.anyBot;

public final class PlayerBuilder {

    private Bot[] team = {anyBot(), anyBot(), anyBot()};
    private UserInteraction ui = new UserInteraction() {
        @Override
        public Bot chooseTarget(Bot... bots) {
            if (bots != null && bots.length != 0) {
                return bots[0];
            } else {
                return null;
            }
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
}
