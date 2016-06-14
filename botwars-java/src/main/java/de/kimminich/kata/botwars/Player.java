package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.SwingUI;
import de.kimminich.kata.botwars.ui.UserInteraction;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Player {

    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    private static int id = 1;

    private final String name = "Player " + id++;
    private final UserInteraction ui;
    private List<Bot> team;

    public Player(List<Bot> roster) {
        this(new SwingUI(), roster);
    }

    public Player(UserInteraction ui, List<Bot> roster) {
        this.ui = ui;
        this.team = pickTeam(roster);
    }

    List<Bot> getTeam() {
        return team;
    }

    Optional<Bot> chooseTarget(List<Bot> opponentTeam) {
        return ui.chooseTarget(opponentTeam);
    }

    private List<Bot> pickTeam(List<Bot> roster) {
        return ui.pickTeam(roster);
    }

    @Override
    public String toString() {
        return name;
    }
}
