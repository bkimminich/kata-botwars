package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;

public class NegativeEffectInflictedMessage {

    private StringBuilder text = new StringBuilder();

    public NegativeEffectInflictedMessage(Bot target, StatusEffect effect) {
        text.append("\n");
        text.append(effect.getClass().getSimpleName()).append(" was inflicted on ").append(target).append("!");
    }

    @Override
    public String toString() {
        return text.toString();
    }

}
