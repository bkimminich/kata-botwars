package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class NeutralStatusEffect extends StatusEffect {

    public NeutralStatusEffect(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
    }

    @Override
    public void revokeEffect(Bot target) {

    }

}
