package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

import java.util.List;
import java.util.Optional;

public class AttackMessage {

    private StringBuilder text = new StringBuilder();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public AttackMessage(Bot attacker, Bot target, Optional<DamageMessage> damageMessage, boolean criticalHit,
                         List<Optional<NegativeEffectInflictedMessage>> statusEffectMessages) {
        text.append(attacker).append(" attacks ").append(target);
        if (criticalHit) {
            text.append(" with Critical Hit!!");
        }
        text.append("! ");
        if (damageMessage.isPresent()) {
            text.append(damageMessage);
        } else {
            text.append(target).append(" successfully evaded!");
        }
        statusEffectMessages.stream().filter(Optional::isPresent).map(Optional::get).forEach(text::append);
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
