package de.kimminich.kata.botwars;

import org.junit.gen5.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.*;

public class PlayerTest {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void playerCanSelectTargetBotFromOpponentTeam() {
        Bot bot = anyBot();
        List<Bot> opponentTeam = Arrays.asList(bot, anyBot(), anyBot());
        Player player = aPlayer().withAttackTarget(bot).build();

        Optional<Bot> choice = player.selectTarget(opponentTeam);

        assertAll(
                () -> assertTrue(choice.isPresent()),
                () -> assertEquals(bot, choice.get())
        );
    }

    @Test
    void playerSelectsBotsFromRoster() {
        Bot bot1 = anyBot();
        Bot bot2 = anyBot();
        Bot bot3 = anyBot();
        Bot bot4 = anyBot();

        Player player = aPlayer().withRoster(bot1, bot2, bot3, bot4).withTeam(bot1, bot2, bot4).build();

        List<Bot> team = player.getTeam();
        assertAll(
                () -> assertEquals(3, team.size()),
                () -> assertTrue(team.contains(bot1)),
                () -> assertTrue(team.contains(bot2)),
                () -> assertTrue(team.contains(bot4)),
                () -> assertFalse(team.contains(bot3))
        );
    }

    @Test
    void playerChoosesName() {
        Player player = aPlayer().withName("Horst").build();

        assertEquals("Horst", player.getName());
    }

}
