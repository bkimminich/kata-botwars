package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class SpeedDown extends StatusEffect {

    private boolean applied = false;

    public SpeedDown(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
        if (!applied) {
            target.setSpeed((int) (target.getSpeed() * 0.75));
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot target) {
        target.setSpeed((int) (target.getSpeed() / 0.75));
    }

}
