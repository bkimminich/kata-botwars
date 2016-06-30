package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractStatusEffect;

public class OffenseDown extends AbstractStatusEffect {

    private boolean applied = false;

    public OffenseDown(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        if (!applied) {
            target.setPower((int) (target.getPower() * 0.75));
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
        target.setPower((int) (target.getPower() / 0.75));
    }

}
