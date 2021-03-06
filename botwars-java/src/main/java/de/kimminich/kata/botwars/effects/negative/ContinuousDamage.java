package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;
import de.kimminich.kata.botwars.messages.Message;

public class ContinuousDamage extends AbstractEffect {

    public ContinuousDamage(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        double evasion = target.getEvasion();
        target.setEvasion(0.0);
        Message damage = target.takeDamage(invoker.getPower() + target.getArmor());
        target.setEvasion(evasion);
        return new GenericTextMessage(damage + " from Continuous Damage effect!");
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        return new EmptyMessage();
    }

}
