package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("An Offense Down effect")
public class OffenseDownTest {

    @Test
    @DisplayName("reduces power by 25% during its duration")
    void reducesPowerBy25Percent() {
        Effect effect = createEffectFactoryFor(anyBot(),
                1, OffenseDown.class).newInstance();
        Bot bot = aBot().withPower(100).withStatusEffects(effect).build();

        bot.applyEffects();
        assertEquals(75, bot.getPower());
        bot.expireEffects();
        assertEquals(100, bot.getPower(), "Power should have been restored after effect expired");
        assertEquals(0, bot.getEffects().size());

    }

}
