package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;
import de.kimminich.kata.botwars.effects.negative.SpeedDown;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("The Speed Down negative status effect")
public class SpeedDownTest {

    @Test
    @DisplayName("reduces speed by 25% during its duration")
    void reducesSpeedBy25Percent() {
        StatusEffect effect = createFactoryForEffectWithDuration(
                1, SpeedDown.class).newInstance();
        Bot bot = aBot().withSpeed(100).withStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(75, bot.getSpeed());
        bot.postMoveActions();
        assertEquals(100, bot.getSpeed(), "Speed should have been restored after effect expired");
        assertEquals(0, bot.getStatusEffects().size());

    }

}
