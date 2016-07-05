package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

@DisplayName("The Continuous Damage effect")
public class ContinuousDamageTest {

    @Test
    @DisplayName("causes damage each turn while the effect is active")
    void causesDamageEachTurn() {
        Effect continuousDamage = createEffectFactoryFor(anyBot(),
                2, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withStatusEffects(continuousDamage).build();

        target.applyEffects();
        int integrityAfterFirstMove = target.getIntegrity();
        assertTrue(integrityAfterFirstMove < 100);
        target.expireEffects();

        target.applyEffects();
        int integrityAfterSecondMove = target.getIntegrity();
        assertTrue(integrityAfterSecondMove < integrityAfterFirstMove);
        target.expireEffects();

        assertEquals(0, target.getEffects().size());

    }

    @Test
    @DisplayName("causes fixed damage equal to power of invoking bot")
    void causesFixedDamageEqualToPowerOfInvoker() {
        Effect continuousDamage = createEffectFactoryFor(
                aBot().withPower(100).build(), 1, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(200).withArmor(30).withStatusEffects(continuousDamage).build();

        target.applyEffects();

        assertEquals(130, target.getIntegrity(), "Should have caused 70 damage from 100-30 armor");

    }

}
