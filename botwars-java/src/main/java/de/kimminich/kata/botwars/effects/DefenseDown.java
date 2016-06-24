package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public class DefenseDown extends NegativeStatusEffect {

    private boolean applied = false;

    DefenseDown(Integer duration) {
        super(duration);
    }

    @Override
    void applyEffect(Bot target) {
        if (!applied) {
            target.setArmor(target.getArmor() / 2);
            target.setResistance(target.getResistance() / 2);
            applied = true;
        }
    }

    @Override
    void revokeEffect(Bot target) {
        target.setArmor(target.getArmor() * 2);
        target.setResistance(target.getResistance() * 2);
    }

}
