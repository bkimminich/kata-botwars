package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class NoStatusEffect extends StatusEffect {

    public NoStatusEffect(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
    }

    @Override
    public void revokeEffect(Bot target) {

    }

}
