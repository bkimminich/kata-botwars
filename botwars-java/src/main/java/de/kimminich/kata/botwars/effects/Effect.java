package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.messages.Message;

public interface Effect {

    boolean isExpired();

    Message apply(Bot target);

    Message revoke(Bot target);

}
