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

    public Game(Player player1, Player player2) throws IllegalArgumentException {
        super();
        validate(player1);
        validate(player2);
    }

    private void validate(Player player) throws IllegalArgumentException {
        if (player.getTeam().length != 3) {
            throw new IllegalArgumentException(player + " team size is invalid: " + player.getTeam().length);
        }
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
