package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserInteraction {

    Optional<Bot> chooseTarget(Player attacker, List<Bot> opponentTeam);

    List<Bot> pickTeam(Player player, Set<Bot> roster);

}
