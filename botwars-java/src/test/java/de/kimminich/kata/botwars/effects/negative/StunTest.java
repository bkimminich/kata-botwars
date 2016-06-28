package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.extensions.InjectMock;
import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Game;
import de.kimminich.kata.botwars.effects.StatusEffect;
import de.kimminich.kata.botwars.effects.negative.Stun;
import de.kimminich.kata.botwars.ui.UserInterface;
import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("The Stun negative status effect")
public class StunTest {

    @Test
    @DisplayName("lets the affected bot miss its next move")
    @Disabled
    void stunnedBotMissesNextMove(@InjectMock UserInterface ui) {
        StatusEffect effect = createFactoryForEffectWithDuration(
                1, Stun.class).newInstance();
        Bot stunnedBot = aBot().withSpeed(1000).withStatusEffects(effect).build();
        Bot target = aBot().withIntegrity(100).build();

        when(ui.selectTarget(eq(stunnedBot), anyListOf(Bot.class))).thenReturn(Optional.of(target));

        Game game = new Game(ui,
                aPlayer().withTeam(stunnedBot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(target, anyBot(), anyBot()).build());

        game.turn();

        assertEquals(100, target.getIntegrity(), "Stunned bot should not damage opponent bot");
        assertEquals(0, stunnedBot.getStatusEffects().size());

    }

    @Test
    @DisplayName("reduces evasion to 0% during its duration")
    void stunnedBotWillNeverEvade() {
        StatusEffect effect = createFactoryForEffectWithDuration(
                1, Stun.class).newInstance();
        Bot bot = aBot().withIntegrity(100).withEvasion(0.1).withStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(0.0, bot.getEvasion());
        bot.postMoveActions();
        assertEquals(0.1, bot.getEvasion(), "Evasion should have been restored after effect expired");
        assertEquals(0, bot.getStatusEffects().size());

    }


}
