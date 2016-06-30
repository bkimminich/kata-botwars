package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public abstract class AbstractStatusEffect implements StatusEffect {

    private Bot invoker;
    private int duration;

    public AbstractStatusEffect(Bot invoker, Integer duration) {
        this.invoker = invoker;
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
        applyEffect(invoker, target);
        duration--;
    }

    @Override
    public void revoke(Bot target) {
        revokeEffect(invoker, target);
    }

    public abstract void applyEffect(Bot invoker, Bot target);

    public abstract void revokeEffect(Bot invoker, Bot target);

    public Bot getInvoker() {
        return invoker;
    }
}
