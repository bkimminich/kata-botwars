package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class NoEffect extends AbstractEffect {

    public NoEffect(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {

    }

}
