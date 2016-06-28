package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class Bomb extends StatusEffect {

    public Bomb(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
    }

    @Override
    public void revokeEffect(Bot target) {
        target.takeDamage(50);
    }

}
