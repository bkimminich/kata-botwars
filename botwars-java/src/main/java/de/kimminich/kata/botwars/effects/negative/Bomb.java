package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.AbstractEffect;
import de.kimminich.kata.botwars.messages.EmptyMessage;
import de.kimminich.kata.botwars.messages.GenericTextMessage;
import de.kimminich.kata.botwars.messages.Message;

import java.util.Random;

public class Bomb extends AbstractEffect {

    private Random random = new Random();

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
        Message damage = target.takeDamage(random.nextInt(invoker.getPower() / 2) + invoker.getPower() / 2);
        target.setResistance(resistance);
        return new GenericTextMessage(damage + " from Bomb effect!");
    }

}
