package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.fail;

@DisplayName("The Continuous Damage negative status effect")
public class ContinuousDamageTest {

    @Test
    @DisplayName("causes damage each turn while the effect is active")
    void causesDamageEachTurn() {
        Effect effect = createEffectFactoryFor(anyBot(),
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

        assertEquals(0, target.getEffects().size());

    }

    @Test
    @DisplayName("causes fixed damage equal to power of invoking bot")
    @Disabled
    void causesFixedDamageEqualToPowerOfInvoker() {
        fail("Not yet implemented");
    }

}
