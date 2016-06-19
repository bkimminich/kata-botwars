package de.kimminich.kata.botwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Game {

    private static final Logger LOG = Logger.getLogger(Game.class.getName());

    private final Player player1;
    private final Player player2;
    private List<Bot> bots = new ArrayList<>(6);

    public Game(Player player1, Player player2) throws IllegalArgumentException {
        super();
        if (player1.getName().equals(player2.getName())) {
            throw new IllegalArgumentException("Players cannot use the same name: " + player1.getName());
        }
        this.player1 = player1;
        this.player2 = player2;
        prepareTeam(player1);
        prepareTeam(player2);
    }

    private void prepareTeam(Player player) throws IllegalArgumentException {
        if (player.getTeam().size() != 3) {
            throw new IllegalArgumentException(player + " team size is invalid: " + player.getTeam().size());
        }
       player.getTeam().stream().filter(i -> Collections.frequency(player.getTeam(), i) > 1)
                .collect(Collectors.toSet()).forEach(bot -> {
           throw new IllegalArgumentException(player + " has duplicate bot in team: " + bot);
       });

       player.getTeam().stream().forEach(bot -> {
            bot.setOwner(player);
            bot.resetBot();
            bots.add(bot);
        });
    }

    void turn() {
        for (Iterator<Bot> it = bots.iterator(); it.hasNext();) {
            Bot bot = it.next();
            if (bot.isDestroyed()) {
                it.remove();
            } else {
                bot.fillTurnMeter();
                if (bot.canTakeTurn()) {
                    LOG.fine(bot + " takes a turn...");
                    bot.depleteTurnMeter();
                    performAttack(bot);
                }
            }
        }
    }

    private void performAttack(Bot attacker) {
        Player attackingPlayer = attacker.getOwner();
        Player opponentPlayer = attackingPlayer == player1 ? player2 : player1;
        Optional<Bot> choice = attackingPlayer.selectTarget(opponentPlayer.getTeam());
        if (choice.isPresent()) {
            Bot target = choice.get();
            attacker.attack(target);
            if (target.isDestroyed()) {
                target.getOwner().getTeam().remove(target);
                LOG.fine(target + " destroyed!");
            }
        }
    }

    public void loop() {
        while (!getWinner().isPresent()) {
            turn();
        }
    }

    public Optional<Player> getWinner() {
        if (player1.getTeam().isEmpty()) {
            LOG.fine(player2 + " wins!");
            return Optional.of(player2);
        } else if (player2.getTeam().isEmpty()) {
            LOG.fine(player1 + " wins!");
            return Optional.of(player1);
        } else {
            return Optional.empty();
        }
    }

    public static void main(String... args) {
        new Game(new Player(), new Player()).loop();
    }

}
