package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;

import java.util.List;

public final class MockUI implements UserInteraction {

    private Bot target;

    private MockUI(Bot target) {
        this.target = target;
    }

    public static MockUI mockTargetChoice(Bot target) {
        return new MockUI(target);
    }

    @Override
    public Bot chooseTarget(List<Bot> bots) {
        return target;
    }
}
