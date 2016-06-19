package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.BotTypes;
import de.kimminich.kata.botwars.Player;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.CLOSED_OPTION;

public class SwingUI implements UserInteraction {

    @Override
    public Optional<Bot> selectTarget(Player attacker, List<Bot> opponentTeam) {
        int choice = JOptionPane.showOptionDialog(null, attacker + ", select bot to attack!\n"
                + toStats(opponentTeam), "Choose target!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opponentTeam.toArray(), opponentTeam.get(0));

        return choice == CLOSED_OPTION ? Optional.empty() : Optional.of(opponentTeam.get(choice));
    }

    private String toStats(List<Bot> opponentTeam) {
        return opponentTeam.stream().map(Bot::toStats).collect(Collectors.joining("\n"));
    }

    @Override
    public List<Bot> selectTeam(Player player, Set<Bot> roster) {
        JList<Bot> list = new JList<>(roster.toArray(new Bot[BotTypes.values().length]));

        JOptionPane.showMessageDialog(
                null, list, player + ", select your team!", JOptionPane.PLAIN_MESSAGE);

        return list.getSelectedValuesList();
    }

    @Override
    public String enterName() {
        return JOptionPane.showInputDialog(null, "Player, enter your name!");
    }
}
