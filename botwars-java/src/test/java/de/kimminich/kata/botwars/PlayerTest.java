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

}
