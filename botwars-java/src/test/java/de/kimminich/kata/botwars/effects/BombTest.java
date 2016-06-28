package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.negative.Bomb;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

@DisplayName("The Bomb negative status effect")
public class BombTest {

    @Test
    @DisplayName("causes damage when it expires")
    void causesDamageWhenExpiring() {
        StatusEffect effect = createFactoryForEffectWithDuration(
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).withStatusEffects(effect).build();

        target.preMoveActions();
        assertEquals(100, target.getIntegrity(), "Bomb should not cause damage while effect is active");
        target.postMoveActions();

        assertTrue(target.getIntegrity() < 100, "Bomb should causes damage when the effect expired");

        assertEquals(0, target.getStatusEffects().size());

    }

}
