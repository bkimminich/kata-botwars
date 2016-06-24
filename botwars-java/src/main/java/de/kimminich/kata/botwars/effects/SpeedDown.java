package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class SpeedDown extends NegativeStatusEffect {

    private boolean applied = false;

    SpeedDown(Integer duration) {
        super(duration);
    }

    @Override
    void applyEffect(Bot target) {
        if (!applied) {
            target.setSpeed((int) (target.getSpeed() * 0.75));
            applied = true;
        }
    }

    @Override
    void revokeEffect(Bot target) {
        target.setSpeed((int) (target.getSpeed() / 0.75));
    }

}
