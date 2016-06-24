package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class NoNegativeStatusEffect extends NegativeStatusEffect {

    protected NoNegativeStatusEffect(Integer duration) {
        super(duration);
    }

    @Override
    void applyEffect(Bot target) {
    }

    @Override
    void revokeEffect(Bot target) {

    }

}
