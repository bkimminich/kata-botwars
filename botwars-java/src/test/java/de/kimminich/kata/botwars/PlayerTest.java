package de.kimminich.kata.botwars;

import org.junit.gen5.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.*;

public class PlayerTest {

    @Test
    void playerCanChooseTargetBotFromOpponentTeam() {
        Bot bot = anyBot();
        List<Bot> opponentTeam = Arrays.asList(bot, anyBot(), anyBot());
        Player player = aPlayer().choosingTarget(bot).build();

        Optional<Bot> choice = player.chooseTarget(opponentTeam);

        assertAll(
                () -> assertTrue(choice.isPresent()),
                () -> assertEquals(bot, choice.get())
        );
    }

    @Test
    void playerPicksBotsFromRoster() {
        Bot bot1 = anyBot();
        Bot bot2 = anyBot();
        Bot bot3 = anyBot();
        Bot bot4 = anyBot();

        Player player = aPlayer().withRoster(bot1, bot2, bot3, bot4).pickingTeam(bot1, bot2, bot4).build();

        List<Bot> team = player.getTeam();
        assertAll(
                () -> assertEquals(3, team.size()),
                () -> assertTrue(team.contains(bot1)),
                () -> assertTrue(team.contains(bot2)),
                () -> assertTrue(team.contains(bot4)),
                () -> assertFalse(team.contains(bot3))
        );
    }

}
