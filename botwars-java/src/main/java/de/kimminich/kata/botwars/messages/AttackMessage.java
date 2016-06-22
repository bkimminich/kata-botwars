package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

public class AttackMessage {

    private StringBuilder text = new StringBuilder();

    public AttackMessage(Bot attacker, Bot target) {
        text.append(attacker).append(" attacks ").append(target).append("!");
    }

    public void criticalHit() {
        text.append(" Critical Hit!!!");
    }

    public void damage(DamageMessage message) {
        text.append("\n").append(message);
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
