package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public abstract class StatusEffect {

    private int duration;

    public StatusEffect(Integer duration) {
        this.duration = duration;
    }

    public boolean isExpired() {
        return duration == 0;
    }

    public void apply(Bot target) {
        applyEffect(target);
        duration--;
    }

    public abstract void applyEffect(Bot target);

    public void revoke(Bot target) {
        revokeEffect(target);
    }

    public abstract void revokeEffect(Bot target);
}
