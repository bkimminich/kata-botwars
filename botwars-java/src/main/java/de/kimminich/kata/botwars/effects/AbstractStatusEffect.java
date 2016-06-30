package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public abstract class AbstractStatusEffect implements StatusEffect {

    private int duration;

    public AbstractStatusEffect(Integer duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean isExpired() {
        return duration == 0;
    }

    @Override
    public void apply(Bot target) {
        applyEffect(target);
        duration--;
    }

    @Override
    public void revoke(Bot target) {
        revokeEffect(target);
    }

    public abstract void applyEffect(Bot target);

    public abstract void revokeEffect(Bot target);

}
