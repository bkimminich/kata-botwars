package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.SwingUI;
import de.kimminich.kata.botwars.ui.UserInteraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private final List<Bot> team = new ArrayList<>(3);
    private final UserInteraction ui;

    public Player(Bot... team) {
        this(new SwingUI(), team);
    }

    public Player(UserInteraction ui, Bot... team) {
        this.ui = ui;
        this.team.addAll(Arrays.asList(team));
    }

    List<Bot> getTeam() {
        return team;
    }

    Bot chooseTarget(List<Bot> opponentTeam) {
        return ui.chooseTarget(opponentTeam);
    }
}
