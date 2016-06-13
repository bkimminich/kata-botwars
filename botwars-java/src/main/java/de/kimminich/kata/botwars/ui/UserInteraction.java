package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;

import java.util.List;

public interface UserInteraction {

    Bot chooseTarget(List<Bot> bots);

    List<Bot> pickTeam(List<Bot> roster);

}
