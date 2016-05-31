package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;

public interface UserInteraction {

    Bot chooseTarget(Bot... bots);

}
