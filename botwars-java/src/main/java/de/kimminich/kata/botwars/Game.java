package de.kimminich.kata.botwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    private Random random = new Random();

    private List<Bot> bots = new ArrayList<>(6);

    public Game(Player player1, Player player2) throws IllegalArgumentException {
        super();
        validate(player1);
        validate(player2);
        bots.addAll(Arrays.asList(player1.getTeam()));
        bots.addAll(Arrays.asList(player2.getTeam()));
        this.bots.forEach(Bot::resetBot);
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
                bots.forEach(bot::attack);
            }
        }
    }

}
