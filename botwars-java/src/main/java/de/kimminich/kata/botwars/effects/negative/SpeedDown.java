package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;

public class SpeedDown extends AbstractEffect {

    private boolean applied = false;

    public SpeedDown(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        if (!applied) {
            target.setSpeed((int) (target.getSpeed() * 0.75));
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
        target.setSpeed((int) (target.getSpeed() / 0.75));
    }

}
