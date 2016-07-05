package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.EffectFactory.createAoEEffectFactoryFor;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.fail;

@DisplayName("The Bomb effect")
public class BombTest {

    @Test
    @DisplayName("causes damage when it expires")
    void causesDamageWhenExpiring() {
        Effect effect = createEffectFactoryFor(anyBot(),
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).withStatusEffects(effect).build();

        target.applyEffects();
        assertEquals(100, target.getIntegrity(), "Bomb should not cause damage while effect is active");
        target.expireEffects();

        assertTrue(target.getIntegrity() < 100, "Bomb should causes damage when the effect expired");

        assertEquals(0, target.getEffects().size());

    }

    @Test
    @DisplayName("causes damage within attack damage range of invoking bot")
    @Disabled
    void causesDamageWithinAttackDamageRangeOfInvoker() {
        fail("Not yet implemented");
    }

    @Nested
    @DisplayName("as an AoE effect")
    class AoE {

        @Test
        @DisplayName("is applied individually to the entire opponent team")
        void isAppliedIndividuallyToTheOpponentTeam() {
            Effect bomb = createAoEEffectFactoryFor(anyBot(),
                    1, Bomb.class).newInstance();
            Bot attacker = aBot().withEffectiveness(1.0).withAttackEffect(Bomb.class).withAoE().build();

            Bot target = aBot().withEvasion(0.0).withResistance(0.0).build();
            Bot teammate = aBot().withResistance(0.0).build();
            Bot immuneTeammate = aBot().withResistance(1.0).build();

            Player opponent = aPlayer().withTeam(target, teammate, immuneTeammate).build();
            opponent.getTeam().stream().forEach(b -> {
                b.setOwner(opponent);
            });

            attacker.attack(target);

            assertAll(
                    () -> assertEquals(1, target.getEffects().size()),
                    () -> assertEquals(1, teammate.getEffects().size(), "Teammate should have Bomb effect"),
                    () -> assertEquals(0, immuneTeammate.getEffects().size(), "Should resist Bomb effect")
            );

            assertAll(
                    () -> assertTrue(target.getEffects().get(0) instanceof Bomb),
                    () -> assertTrue(teammate.getEffects().get(0) instanceof Bomb)
            );
        }
    }

}
