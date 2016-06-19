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
        enterName();
        this.team = selectTeam(roster);
    }

    List<Bot> getTeam() {
        return team;
    }

    Optional<Bot> selectTarget(List<Bot> opponentTeam) {
        return ui.selectTarget(this, opponentTeam);
    }

    private List<Bot> selectTeam(Set<Bot> roster) {
        return ui.selectTeam(this, roster);
    }

    private void enterName() {
        this.name = ui.enterName();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
