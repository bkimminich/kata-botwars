package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.SwingUI;
import de.kimminich.kata.botwars.ui.UserInteraction;

public class Player {

    private final Bot[] team;
    private final UserInteraction ui;

    public Player(Bot... team) {
        this(new SwingUI(), team);
    }

    Player(UserInteraction ui, Bot... team) {
        this.ui = ui;
        this.team = team;
    }

    public Bot[] getTeam() {
        return team;
    }

    public Bot chooseTarget(Bot[] opponentTeam) {
        return ui.chooseTarget(opponentTeam);
    }
}
