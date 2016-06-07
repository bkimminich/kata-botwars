package de.kimminich.kata.botwars;

import org.junit.gen5.api.Test;

import java.util.Arrays;
import java.util.List;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.ui.MockUI.mockTargetChoice;
import static org.junit.gen5.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    void playerCanChooseTargetBotFromOpponentTeam() {
        Bot bot = anyBot();

        Player player = aPlayer().withUI(mockTargetChoice(bot)).build();

        List<Bot> opponentTeam = Arrays.asList(bot, anyBot(), anyBot());

        assertEquals(bot, player.chooseTarget(opponentTeam));
    }

}
