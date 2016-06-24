package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class OffenseDown extends StatusEffect {

    private boolean applied = false;

    public OffenseDown(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
        if (!applied) {
            target.setPower((int) (target.getPower() * 0.75));
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot target) {
        target.setPower((int) (target.getPower() / 0.75));
    }

}
