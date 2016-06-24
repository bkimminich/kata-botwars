package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class DefenseDown extends StatusEffect {

    private boolean applied = false;

    public DefenseDown(Integer duration) {
        super(duration);
    }

    @Override
    public void applyEffect(Bot target) {
        if (!applied) {
            target.setArmor(target.getArmor() / 2);
            target.setResistance(target.getResistance() / 2);
            applied = true;
        }
    }

    @Override
    public void revokeEffect(Bot target) {
        target.setArmor(target.getArmor() * 2);
        target.setResistance(target.getResistance() * 2);
    }

}
