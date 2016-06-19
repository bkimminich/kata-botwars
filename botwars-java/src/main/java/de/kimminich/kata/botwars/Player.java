package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.SwingUI;
import de.kimminich.kata.botwars.ui.UserInteraction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class Player {

    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    private String name;
    private final UserInteraction ui;
    private List<Bot> team;

    public Player() {
        this(new SwingUI(), BotFactory.createDefaultRoster());
    }

    public Player(UserInteraction ui, Set<Bot> roster) {
        this.ui = ui;
        chooseName();
        this.team = pickTeam(roster);
    }

    List<Bot> getTeam() {
        return team;
    }

    Optional<Bot> chooseTarget(List<Bot> opponentTeam) {
        return ui.chooseTarget(this, opponentTeam);
    }

    private List<Bot> pickTeam(Set<Bot> roster) {
        return ui.pickTeam(this, roster);
    }

    private void chooseName() {
        this.name = ui.chooseName();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
