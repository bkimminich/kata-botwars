package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.BotTypes;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SwingUI implements UserInteraction {

    @Override
    public Optional<Bot> chooseTarget(List<Bot> bots) {
        JList<Bot> list = new JList<>(bots.toArray(new Bot[3]));

        JOptionPane.showMessageDialog(
                null, list, "Choose target bot to attack", JOptionPane.PLAIN_MESSAGE);

        return list.getSelectedValue() != null ? Optional.of(list.getSelectedValue()) : Optional.empty();
    }

    @Override
    public List<Bot> pickTeam(Set<Bot> roster) {
        JList<Bot> list = new JList<>(roster.toArray(new Bot[BotTypes.values().length]));

        JOptionPane.showMessageDialog(
                null, list, "Pick a team of three bots", JOptionPane.PLAIN_MESSAGE);

        return list.getSelectedValuesList();
    }
}
