package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

class OffenseDown extends NegativeStatusEffect {

    private boolean applied = false;

    OffenseDown(Integer duration) {
        super(duration);
    }

    @Override
    void applyEffect(Bot target) {
        if (!applied) {
            target.setPower((int) (target.getPower() * 0.75));
            applied = true;
        }
    }

    @Override
    void revokeEffect(Bot target) {
        target.setPower((int) (target.getPower() / 0.75));
    }

}
