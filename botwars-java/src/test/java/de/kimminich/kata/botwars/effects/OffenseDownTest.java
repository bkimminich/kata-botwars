package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.NegativeStatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("The Offense Down negative status effect")
public class OffenseDownTest {

    @Test
    @DisplayName("reduces power by 25% during its duration")
    void reducesPowerBy25Percent() {
        NegativeStatusEffect effect = createFactoryForEffectWithDuration(
                1, OffenseDown.class).newInstance();
        Bot bot = aBot().withPower(100).withNegativeStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(75, bot.getPower());
        bot.postMoveActions();
        assertEquals(100, bot.getPower(), "Power should have been restored after effect expired");
        assertEquals(0, bot.getNegativeStatusEffects().size());

    }

}
