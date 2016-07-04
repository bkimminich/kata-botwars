package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

public class DamageMessage {

    private StringBuilder text = new StringBuilder();

    public DamageMessage(Bot target, int damage) {
        text.append(target).append(" takes ").append(damage).append(" damage!");
    }

    @Override
    public String toString() {
        return text.toString();
    }

}
