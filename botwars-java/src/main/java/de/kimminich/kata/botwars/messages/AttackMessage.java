package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

public class AttackMessage {

    private StringBuilder text = new StringBuilder();

    public AttackMessage(Bot attacker, Bot target, DamageMessage damage, boolean criticalHit) {
        text.append(attacker).append(" attacks ").append(target);
        if (criticalHit) {
            text.append(" with Critical Hit!!");
        }
        text.append("! ");
        text.append(damage);
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
