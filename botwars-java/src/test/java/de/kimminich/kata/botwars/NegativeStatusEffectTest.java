package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.NegativeStatusEffect;
import de.kimminich.kata.botwars.effects.NoNegativeStatusEffect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.DynamicTest;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.TestFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.NegativeStatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.DynamicTest.dynamicTest;

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
        Bot bot = aBot().withEffectiveness(1.0).withAttackEffectAndDuration(NoNegativeStatusEffect.class, 1).build();
        Bot opponent = aBot().withResistance(0.0).withNoNegativeStatusEffects().build();

        bot.attack(opponent);

        assertAll(
                () -> assertEquals(1, opponent.getNegativeStatusEffects().size()),
                () -> assertTrue(NoNegativeStatusEffect.class.isAssignableFrom(
                        opponent.getNegativeStatusEffects().get(0).getClass()),
                        "Inflicted effect should have type NoNegativeStatusEffect")
        );

    }

    @TestFactory
    @DisplayName("expires after the duration of ")
    Stream<DynamicTest> negativeStatusEffectsExpireAfterDuration() {

        return IntStream.range(1, 4).mapToObj(duration ->
                dynamicTest(duration + " moves of the affected bot", () -> {
                    NegativeStatusEffect effect = createFactoryForEffectWithDuration(
                            NoNegativeStatusEffect.class, duration).newInstance();
                    Bot target = aBot().withNegativeStatusEffects(effect).build();

                    for (int i = 0; i < duration; i++) {
                        assertFalse(effect.isExpired(), "Effect should not expire after " + i + " moves");
                        target.preMoveActions();
                    }
                    assertTrue(effect.isExpired());
                }));

    }

}
