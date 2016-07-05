package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.Message;

public class NoEffect extends AbstractEffect {

    public NoEffect(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        return new EmptyMessage();
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        return new EmptyMessage();
    }

}
