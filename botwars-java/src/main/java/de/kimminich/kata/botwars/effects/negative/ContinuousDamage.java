package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;

public class ContinuousDamage extends AbstractEffect {

    public ContinuousDamage(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        double evasion = target.getEvasion();
        target.setEvasion(0.0);
        target.takeDamage(50);
        target.setEvasion(evasion);
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
    }

}
