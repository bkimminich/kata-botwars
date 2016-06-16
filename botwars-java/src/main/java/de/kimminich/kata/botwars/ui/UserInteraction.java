package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserInteraction {

    Optional<Bot> chooseTarget(List<Bot> bots);

    List<Bot> pickTeam(Set<Bot> roster);

}
