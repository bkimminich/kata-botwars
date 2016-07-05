package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;
import de.kimminich.kata.botwars.messages.Message;

public class Bomb extends AbstractEffect {

    public Bomb(Bot invoker, Integer duration) {
        super(invoker, duration);
    }

    @Override
    public Message applyEffect(Bot invoker, Bot target) {
        return new EmptyMessage();
    }

    @Override
    public Message revokeEffect(Bot invoker, Bot target) {
        double resistance = target.getResistance();
        target.setResistance(1.0);
        Message damage = invoker.attack(target);
        target.setResistance(resistance);
        return new GenericTextMessage("Caused by Bomb, " + damage);
    }

}
