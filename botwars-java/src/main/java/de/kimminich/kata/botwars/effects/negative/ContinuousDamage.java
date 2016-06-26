package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class ContinuousDamage extends StatusEffect {

    public ContinuousDamage(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
        double evasion = target.getEvasion();
        target.setEvasion(0.0);
        target.takeDamage(50);
        target.setEvasion(evasion);
    }

    @Override
    public void revokeEffect(Bot target) {
    }

}
