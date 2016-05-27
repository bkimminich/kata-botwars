package de.kimminich.kata.botwars;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    private Random random = new Random();

    private List<Bot> bots;

    public Game(Bot... bots) {
        this.bots = Arrays.asList(bots);
        this.bots.forEach(Bot::resetBot);
    }

    public void turn() {
        for (Bot bot : bots) {
            bot.fillTurnMeter();
            if (bot.canTakeTurn()) {
              bot.depleteTurnMeter();
              // FIXME Only damage one other bot, not all at once!
                bots.forEach(bot::causeDamage);
            }
        }
    }

}
