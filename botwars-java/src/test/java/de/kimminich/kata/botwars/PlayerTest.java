package de.kimminich.kata.botwars;

import org.junit.gen5.api.Test;

import java.util.Arrays;
import java.util.List;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    void playerCanChooseTargetBotFromOpponentTeam() {
        Bot bot = anyBot();
        List<Bot> opponentTeam = Arrays.asList(bot, anyBot(), anyBot());

        Player player = aPlayer().choosingTarget(bot).build();

        assertEquals(bot, player.chooseTarget(opponentTeam));
    }

}
