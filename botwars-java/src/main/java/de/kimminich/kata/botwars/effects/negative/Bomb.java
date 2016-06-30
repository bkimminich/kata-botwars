package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractStatusEffect;

public class Bomb extends AbstractStatusEffect {

    public Bomb(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
        target.takeDamage(50);
    }

}
