package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SwingUI implements UserInteraction {

    @Override
    public Optional<Bot> chooseTarget(List<Bot> bots) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Bot> pickTeam(Set<Bot> roster) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
