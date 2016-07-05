package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.messages.Message;

public abstract class AbstractEffect implements Effect {

    private Bot invoker;
    private int duration;

    public AbstractEffect(Bot invoker, Integer duration) {
        this.invoker = invoker;
        this.duration = duration;
    }

    @Override
    public boolean isExpired() {
        return duration == 0;
    }

    @Override
    public Message apply(Bot target) {
        duration--;
        return applyEffect(invoker, target);
    }

    @Override
    public Message revoke(Bot target) {
        return revokeEffect(invoker, target);
    }

    public abstract Message applyEffect(Bot invoker, Bot target);

    public abstract Message revokeEffect(Bot invoker, Bot target);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + duration + ")";
    }
}
