package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.Message;

public class DefenseDown extends AbstractEffect {

    private boolean applied = false;

    public DefenseDown(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        if (!applied) {
            target.setArmor(target.getArmor() / 2);
            target.setResistance(target.getResistance() / 2);
            applied = true;
        }
        return new EmptyMessage();
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        target.setArmor(target.getArmor() * 2);
        target.setResistance(target.getResistance() * 2);
        return new EmptyMessage();
    }

}
