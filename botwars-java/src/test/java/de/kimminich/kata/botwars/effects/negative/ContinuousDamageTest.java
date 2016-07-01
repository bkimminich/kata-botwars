package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.*;

@DisplayName("The Continuous Damage negative status effect")
public class ContinuousDamageTest {

    @Test
    @DisplayName("causes damage each turn while the effect is active")
    void causesDamageEachTurn() {
        StatusEffect effect = createFactoryForEffectWithDuration(anyBot(),
                2, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withStatusEffects(effect).build();

        target.preMoveActions();
        int integrityAfterFirstMove = target.getIntegrity();
        assertTrue(integrityAfterFirstMove < 100);
        target.postMoveActions();

        target.preMoveActions();
        int integrityAfterSecondMove = target.getIntegrity();
        assertTrue(integrityAfterSecondMove < integrityAfterFirstMove);
        target.postMoveActions();

        assertEquals(0, target.getStatusEffects().size(), "Continuous Damage should expire after 2 moves");

    }

    @Test
    @DisplayName("causes fixed damage equal to power of invoking bot")
    void causesFixedDamageEqualToPowerOfInvoker() {
        Bot invoker = aBot().withPower(200).build();
        StatusEffect effect = createFactoryForEffectWithDuration(invoker,
                1, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(500).withArmor(0).withStatusEffects(effect).build();

        target.preMoveActions();
        assertEquals(300, target.getIntegrity());
    }

    @Test
    @DisplayName("is reduced by armor")
    void damageIsReducedByArmor() {
        Bot invoker = aBot().withPower(100).build();
        StatusEffect effect = createFactoryForEffectWithDuration(invoker,
                1, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(150).withArmor(10).withStatusEffects(effect).build();

        target.preMoveActions();
        assertEquals(60, target.getIntegrity());

    }

}
