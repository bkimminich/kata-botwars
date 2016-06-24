package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public abstract class NegativeStatusEffect {

    private int duration;

    NegativeStatusEffect(Integer duration) {
        this.duration = duration;
    }

    public boolean isExpired() {
        return duration == 0;
    }

    public void apply(Bot target) {
        applyEffect(target);
        duration--;
    }

    abstract void applyEffect(Bot target);

    public void revoke(Bot target) {
        revokeEffect(target);
    }

    abstract void revokeEffect(Bot target);
}
