package de.kimminich.kata.botwars;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Game {

    private Random random = new Random();

    private final Player player1;
    private final Player player2;
    private List<Bot> bots = new ArrayList<>(6);

    public Game(Player player1, Player player2) throws IllegalArgumentException {
        super();
        this.player1 = player1;
        this.player2 = player2;
        prepareTeam(player1);
        prepareTeam(player2);
    }

    private void prepareTeam(Player player) throws IllegalArgumentException {
        if (player.getTeam().size() != 3) {
            throw new IllegalArgumentException(player + " team size is invalid: " + player.getTeam().size());
        }
       player.getTeam().stream().forEach(bot -> {
            bot.setOwner(player);
            bot.resetBot();
            bots.add(bot);
        });
    }

    void turn() {
        for (Bot bot : bots) {
            bot.fillTurnMeter();
            if (bot.canTakeTurn()) {
                bot.depleteTurnMeter();
                performAttack(bot);
            }
        }
    }

    private void performAttack(Bot bot) {
        Player owner = bot.getOwner();
        Player opponent = owner == player1 ? player2 : player1;
        Bot target = owner.chooseTarget(opponent.getTeam());
        bot.attack(target);
        if (target.isDestroyed()) {
            opponent.getTeam().remove(target);
        }
    }

    public void loop() {
        while (!getWinner().isPresent()) {
            turn();
        }
    }

    public Optional<Player> getWinner() {
        if (player1.getTeam().isEmpty()) {
            return Optional.of(player2);
        } else if (player2.getTeam().isEmpty()) {
            return Optional.of(player1);
        } else {
            return Optional.empty();
        }
    }
}
