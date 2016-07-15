package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

import java.util.List;

public class AttackMessage implements Message {

    private StringBuilder text = new StringBuilder();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public AttackMessage(Bot attacker, Bot target, Message damageMessage, boolean criticalHit,
                         List<Message> statusEffectMessages) {
        text.append(attacker).append(" attacks ").append(target);
        if (criticalHit) {
            text.append(" with Critical Hit!!");
        }
        text.append("! ");
        text.append(damageMessage);
        text.append("!");
        statusEffectMessages.stream().forEach(text::append);
    }

    @Override
    public String getMessage() {
        return text.toString();
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
