package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;

public interface StatusEffect {

    boolean isExpired();

    void apply(Bot target);

    void revoke(Bot target);

}
