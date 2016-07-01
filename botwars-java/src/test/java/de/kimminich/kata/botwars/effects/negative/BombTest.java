package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.*;

@DisplayName("The Bomb negative status effect")
public class BombTest {

    @Test
    @DisplayName("causes damage when it expires")
    void causesDamageWhenExpiring() {
        StatusEffect effect = createFactoryForEffectWithDuration(anyBot(),
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).withStatusEffects(effect).build();

        target.preMoveActions();
        assertEquals(100, target.getIntegrity(), "Bomb should not cause damage while effect is active");
        target.postMoveActions();
        assertTrue(target.getIntegrity() < 100, "Bomb should causes damage when the effect expired");

    }

    @Test
    @DisplayName("causes damage within attack damage range of invoking bot")
    void causesDamageWithinAttackDamageRangeOfInvoker() {
        Bot invoker = aBot().withPower(100).withCriticalHit(0.0).build();

        for (int i = 0; i < 1000; i++) {
            StatusEffect effect = createFactoryForEffectWithDuration(invoker,
                    1, Bomb.class).newInstance();
            Bot target = aBot().withIntegrity(200).withArmor(0).withStatusEffects(effect).build();

            target.preMoveActions();
            assertTrue(target.getIntegrity() <= 150);
            assertTrue(target.getIntegrity() >= 100);
        }
    }

    @Test
    @DisplayName("reduces damage by target armor")
    void reducesDamageByTargetArmor() {
        Bot invoker = aBot().withPower(100).withCriticalHit(0.0).build();

        StatusEffect effect = createFactoryForEffectWithDuration(invoker,
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(200).withArmor(60).withStatusEffects(effect).build();

        target.preMoveActions();
        assertTrue(target.getIntegrity() <= 200);
        assertTrue(target.getIntegrity() >= 160);
    }

    @Test
    @DisplayName("doubles damage for critical hits")
    void doublesDamageForCriticalHits() {
        Bot invoker = aBot().withPower(100).withCriticalHit(1.0).build();

        StatusEffect effect = createFactoryForEffectWithDuration(invoker,
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(201).withArmor(0).withStatusEffects(effect).build();

        target.preMoveActions();
        assertTrue(target.getIntegrity() <= 101);
        assertTrue(target.getIntegrity() >= 1);
    }

}
