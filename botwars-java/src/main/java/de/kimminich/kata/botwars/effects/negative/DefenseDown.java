package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;

public class DefenseDown extends AbstractEffect {

    private boolean applied = false;

    public DefenseDown(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public void applyEffect(Bot invoker, Bot target) {
        if (!applied) {
            target.setArmor(target.getArmor() / 2);
            target.setResistance(target.getResistance() / 2);
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot invoker, Bot target) {
        target.setArmor(target.getArmor() * 2);
        target.setResistance(target.getResistance() * 2);
    }

}
