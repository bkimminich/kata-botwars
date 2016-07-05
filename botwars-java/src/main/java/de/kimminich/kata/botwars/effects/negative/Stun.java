package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;

public class Stun extends AbstractEffect {

    private double evasion;

    public Stun(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        evasion = target.getEvasion();
        target.setEvasion(0.0);
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
        target.setEvasion(evasion);
    }

}
