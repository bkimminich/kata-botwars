package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
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
    @DisplayName("causes damage each turn during its duration")
    void causesDamageEachTurn() {
        Bot invoker = aBot().withPower(50).build();
        StatusEffect effect = createFactoryForEffectWithDuration(
                2, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(10).withStatusEffects(effect).build();

        target.preMoveActions();
        assertTrue(target.getIntegrity() == 60);
        target.postMoveActions();

        target.preMoveActions();
        assertTrue(target.getIntegrity() == 20);
        target.postMoveActions();

        assertEquals(0, target.getStatusEffects().size());

    }

}
