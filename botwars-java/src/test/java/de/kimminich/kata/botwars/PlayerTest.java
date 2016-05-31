package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.ui.UserInteraction;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    void playerCanChooseTargetBotFromOpponentTeam() {
        Player player = aPlayer().withUI(new UserInteraction() {
            @Override
            public Bot chooseTarget(Bot... bots) {
                return bots[0];
            }
        }).build();

        Bot[] opponentTeam = {anyBot(), anyBot(), anyBot()};

        assertEquals(opponentTeam[0], player.chooseTarget(opponentTeam));
    }

}
