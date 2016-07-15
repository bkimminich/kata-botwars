package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;
import de.kimminich.kata.botwars.messages.Message;

public class Stun extends AbstractEffect {

    private double evasion;

    public Stun(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        evasion = target.getEvasion();
        target.setEvasion(0.0);
        return new GenericTextMessage("Stunned " + target + " is unable to attack!");
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        target.setEvasion(evasion);
        return new EmptyMessage();
    }

}
