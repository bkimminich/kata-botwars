package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.NegativeStatusEffect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.NegativeStatusEffectBuilder.anyNegativeStatusEffect;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

@DisplayName("A negative status effect")
public class NegativeStatusEffectTest {

    @Test
    @DisplayName("is mitigated when a bot resists it")
    void resistanceMitigatesAllNegativeStatusEffects() {
        Bot bot = aBot().withEffectiveness(1.0).build();
        Bot opponent = aBot().withResistance(1.0).withNoNegativeStatusEffects().build();

        bot.attack(opponent);

        assertEquals(0, opponent.getNegativeStatusEffects().size());

    }

    @Test
    @DisplayName("is inflicted on a bot when it does not resist")
    void failingToResistInflictsNegativeStatusEffects() {
        Class<? extends NegativeStatusEffect> effect = anyNegativeStatusEffect().getClass();
        Bot bot = aBot().withEffectiveness(1.0).withEffectOnAttack(effect).build();
        Bot opponent = aBot().withResistance(0.0).withNoNegativeStatusEffects().build();

        bot.attack(opponent);

        assertAll(
                () -> assertEquals(1, opponent.getNegativeStatusEffects().size()),
                () -> assertTrue(effect.isAssignableFrom(opponent.getNegativeStatusEffects().get(0).getClass()))
        );

    }

}
