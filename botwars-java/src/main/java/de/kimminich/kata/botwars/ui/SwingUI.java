package de.kimminich.kata.botwars.ui;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.BotTypes;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.reports.AttackReport;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.CLOSED_OPTION;

public class SwingUI implements UserInterface {

    @Override
    public Optional<Bot> selectTarget(Bot attacker, List<Bot> opponentTeam) {
        int choice = JOptionPane.showOptionDialog(null, attacker.getOwner() + ", select bot to attack!\n"
                + toStats(opponentTeam), attacker + " makes a move!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opponentTeam.toArray(), opponentTeam.get(0));

        return choice == CLOSED_OPTION ? Optional.empty() : Optional.of(opponentTeam.get(choice));
    }

    private String toStats(List<Bot> opponentTeam) {
        return opponentTeam.stream().map(Bot::getStatus).map(Object::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public List<Bot> selectTeam(Set<Bot> roster) {
        JList<Bot> list = new JList<>(roster.toArray(new Bot[BotTypes.values().length]));

        JOptionPane.showMessageDialog(
                null, list, "Player, select your team!", JOptionPane.PLAIN_MESSAGE);

        return list.getSelectedValuesList();
    }

    @Override
    public String enterName() {
        return JOptionPane.showInputDialog(null, "Player, enter your name!");
    }

    @Override
    public void gameOver(Player winner) {
        JOptionPane.showMessageDialog(null, winner + " wins the game!",
                "Game over!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void attackReport(AttackReport report) {
        JOptionPane.showMessageDialog(null, report, "Attack Report", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void botDestruction(Bot target) {
        JOptionPane.showMessageDialog(null, target + " has been destroyed!!!",
                target + " destroyed!", JOptionPane.ERROR_MESSAGE);
    }
}
