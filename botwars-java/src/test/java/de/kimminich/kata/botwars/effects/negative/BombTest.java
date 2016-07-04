package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.effects.StatusEffect;
import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDurationAndAoE;
import static org.junit.gen5.api.Assertions.assertAll;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.fail;

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

        assertEquals(0, target.getStatusEffects().size());

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
            StatusEffect bomb = createFactoryForEffectWithDurationAndAoE(anyBot(),
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
                    () -> assertEquals(1, target.getStatusEffects().size()),
                    () -> assertEquals(1, teammate.getStatusEffects().size(), "Teammate should have Bomb effect"),
                    () -> assertEquals(0, immuneTeammate.getStatusEffects().size(), "Should resist Bomb effect")
            );

            assertAll(
                    () -> assertTrue(target.getStatusEffects().get(0) instanceof Bomb),
                    () -> assertTrue(teammate.getStatusEffects().get(0) instanceof Bomb)
            );
        }
    }

}
