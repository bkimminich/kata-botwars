package de.kimminich.kata.botwars.messages;

import de.kimminich.kata.botwars.Bot;

import java.util.List;
import java.util.Optional;

public class AttackMessage {

    private StringBuilder text = new StringBuilder();

    public AttackMessage(Bot attacker, Bot target, DamageMessage damage, boolean criticalHit,
                         List<Optional<NegativeEffectInflictedMessage>> statusEffectMessages) {
        text.append(attacker).append(" attacks ").append(target);
        if (criticalHit) {
            text.append(" with Critical Hit!!");
        }
        text.append("! ");
        text.append(damage);
        statusEffectMessages.stream().filter(Optional::isPresent).map(Optional::get).forEach(text::append);
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
