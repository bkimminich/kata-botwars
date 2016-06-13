package de.kimminich.kata.botwars;

import de.kimminich.extensions.InjectMock;
import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.ui.UserInteraction;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerTest {

    @Test
    void playerCanChooseTargetBotFromOpponentTeam(@InjectMock UserInteraction ui) {
        Bot bot = anyBot();
        List<Bot> opponentTeam = Arrays.asList(bot, anyBot(), anyBot());
        when(ui.chooseTarget(opponentTeam)).thenReturn(bot);

        Player player = aPlayer().withUI(ui).build();

        assertEquals(bot, player.chooseTarget(opponentTeam));
    }

}
