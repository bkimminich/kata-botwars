package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.Message;

public class OffenseDown extends AbstractEffect {

    private boolean applied = false;

    public OffenseDown(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        if (!applied) {
            target.setPower((int) (target.getPower() * 0.75));
            applied = true;
        }
        return new EmptyMessage();
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        target.setPower((int) (target.getPower() / 0.75));
        return new EmptyMessage();
    }

}
