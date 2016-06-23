package de.kimminich.kata.botwars;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("A negative status effect")
public class NegativeStatusEffectTest {

    @Test
    @DisplayName("is mitigated when a bot resists it")
    void resistanceMitigatesAllNegativeStatusEffects() {
        Bot bot = aBot().withEffectiveness(1.0).build();
        Bot opponent = aBot().withResistance(1.0).withNoNegativeStatusEffects().build();

        bot.attack(opponent);

        assertEquals(0, bot.getNegativeStatusEffects().size());

    }

}
