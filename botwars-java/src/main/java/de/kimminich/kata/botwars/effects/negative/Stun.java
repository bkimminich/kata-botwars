package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class Stun extends StatusEffect {

    private double evasion;

    public Stun(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
        evasion = target.getEvasion();
        target.setEvasion(0.0);
    }

    @Override
    public void revokeEffect(Bot target) {
        target.setEvasion(evasion);
    }

}
