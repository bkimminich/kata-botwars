package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.negative.DefenseDown;
import de.kimminich.kata.botwars.effects.negative.OffenseDown;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.DynamicTest;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.TestFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.DynamicTest.dynamicTest;

@DisplayName("An effect")
public class EffectTest {

    @Test
    @DisplayName("is mitigated when an attacked bot resists it")
    void resistanceMitigatesStatusEffect() {
        Bot bot = aBot().withEffectiveness(1.0).build();
        Bot opponent = aBot().withResistance(1.0).withNoStatusEffects().build();

        bot.attack(opponent);

        assertEquals(0, opponent.getEffects().size());

    }

    @Test
    @DisplayName("is inflicted on an attacked bot if it does not resist")
    void failingToResistInflictsStatusEffect() {
        Bot bot = aBot().withEffectiveness(1.0).withAttackEffect(NoEffect.class).withEffectDuration(1).build();
        Bot opponent = aBot().withResistance(0.0).withNoStatusEffects().build();

        bot.attack(opponent);

        assertAll(
                () -> assertEquals(1, opponent.getEffects().size()),
                () -> assertTrue(NoEffect.class.isAssignableFrom(
                        opponent.getEffects().get(0).getClass()),
                        "Inflicted effect should have type NoNegativeStatusEffect")
        );

    }

    @TestFactory
    @DisplayName("expires after the duration of ")
    Stream<DynamicTest> statusEffectsExpireAfterDuration() {

        return IntStream.range(1, 4).mapToObj(duration ->
                dynamicTest(duration + " moves of the affected bot", () -> {
                    Effect effect = createEffectFactoryFor(anyBot(),
                            duration, NoEffect.class).newInstance();
                    Bot target = aBot().withStatusEffects(effect).build();

                    for (int i = 0; i < duration; i++) {
                        assertFalse(effect.isExpired(), "Effect should not expire after " + i + " moves");
                        target.applyEffects();
                    }
                    assertTrue(effect.isExpired());
                }));

    }

    @Test
    @DisplayName("is picked from a supplied list of possible effects")
    void picksOneRandomStatusEffectFromSuppliedList() {
        int offenseDownChosen = 0;
        int defenseDownChosen = 0;
        EffectFactory factory = createEffectFactoryFor(anyBot(), 1,
                OffenseDown.class, DefenseDown.class);

        for (int i = 0; i < 1000; i++) {
            Effect effect = factory.newInstance();
            assertTrue(effect instanceof OffenseDown || effect instanceof DefenseDown);
            if (effect instanceof OffenseDown) {
                offenseDownChosen++;
            } else {
                defenseDownChosen++;
            }
        }
        int totalOffenseDownChosen = offenseDownChosen;
        int totalDefenseDownChosen = defenseDownChosen;
        assertAll(
                () -> assertTrue(totalOffenseDownChosen < 700),
                () -> assertTrue(totalOffenseDownChosen > 300),
                () -> assertTrue(totalDefenseDownChosen < 700),
                () -> assertTrue(totalDefenseDownChosen > 300)
        );
    }

    @Test
    @DisplayName("is never inflicted on a bot that successfully evaded")
    void neverInflictedOnEvadingBot() {
        Bot attacker = aBot().withEffectiveness(1.0).withAttackEffect(NoEffect.class).build();
        Bot target = aBot().withEvasion(1.0).withResistance(0.0).withNoStatusEffects().build();

        attacker.attack(target);

        assertEquals(0, target.getEffects().size());
    }


}
