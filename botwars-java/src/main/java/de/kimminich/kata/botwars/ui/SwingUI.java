package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.BotTypes;
import de.kimminich.kata.botwars.Player;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static javax.swing.JOptionPane.CLOSED_OPTION;

public class SwingUI implements UserInteraction {

    @Override
    public Optional<Bot> chooseTarget(Player attacker, List<Bot> opponentTeam) {
        int choice = JOptionPane.showOptionDialog(null, attacker + ", choose bot to attack!", "Choose target!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opponentTeam.toArray(), opponentTeam.get(0));

        return choice == CLOSED_OPTION ? Optional.empty() : Optional.of(opponentTeam.get(choice));
    }

    @Override
    public List<Bot> pickTeam(Player player, Set<Bot> roster) {
        JList<Bot> list = new JList<>(roster.toArray(new Bot[BotTypes.values().length]));

        JOptionPane.showMessageDialog(
                null, list, player + ", pick your team!", JOptionPane.PLAIN_MESSAGE);

        return list.getSelectedValuesList();
    }
}
