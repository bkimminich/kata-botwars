package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractStatusEffect;

public class ContinuousDamage extends AbstractStatusEffect {

    public ContinuousDamage(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        double evasion = target.getEvasion();
        target.setEvasion(0.0);
        target.takeDamage(invoker.getPower());
        target.setEvasion(evasion);
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
    }

}
