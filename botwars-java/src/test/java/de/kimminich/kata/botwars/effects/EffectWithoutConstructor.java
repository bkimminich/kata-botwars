package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.messages.Message;

class EffectWithoutConstructor implements Effect {
    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Message apply(Bot target) {
        return null;
    }

    @Override
    public Message revoke(Bot target) {
        return null;
    }
}
