package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;
import de.kimminich.kata.botwars.effects.negative.ContinuousDamage;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

@DisplayName("The Continuous Damage negative status effect")
public class ContinuousDamageTest {

    @Test
    @DisplayName("causes damage each turn while the effect is active")
    void causesDamageEachTurn() {
        StatusEffect effect = createFactoryForEffectWithDuration(
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

        assertEquals(0, target.getStatusEffects().size());

    }

}
