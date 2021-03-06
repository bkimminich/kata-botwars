package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A Bomb effect")
public class BombTest {

    @Test
    @DisplayName("causes damage when it expires")
    void causesDamageWhenExpiring() {
        Effect bomb = createEffectFactoryFor(anyBot(),
                1, Bomb.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).withStatusEffects(bomb).build();

        target.applyEffects();
        assertEquals(100, target.getIntegrity(), "Bomb should not cause damage while effect is active");
        target.expireEffects();

        assertTrue(target.getIntegrity() < 100, "Bomb should causes damage when the effect expired");

        assertEquals(0, target.getEffects().size());

    }

    @Test
    @DisplayName("causes damage within attack damage range of invoking bot")
    void causesDamageWithinAttackDamageRangeOfInvoker() {
        for (int i = 0; i < 1000; i++) {
            Effect bomb = createEffectFactoryFor(aBot().withPower(100).withCriticalHit(0.0).build(),
                    0, Bomb.class).newInstance();
            Bot target = aBot().withIntegrity(100).withArmor(10).withEvasion(0.0).withStatusEffects(bomb).build();

            target.expireEffects();

            assertAll(
                    () -> assertTrue(target.getIntegrity() >= 10, "Damage should be max. 90 from (50 to 100)-10 armor"),
                    () -> assertTrue(target.getIntegrity() <= 60, "Damage should be min. 40 from (50 to 100)-10 armor")
            );
        }
    }

    @Nested
    @DisplayName("as an AoE effect")
    class AoE {

        @Test
        @DisplayName("is applied individually to the entire opponent team")
        void isAppliedIndividuallyToTheOpponentTeam() {
            Bot attacker = aBot().withEffectiveness(1.0).withAttackEffect(Bomb.class).withAoE().build();

            Bot target = aBot().withEvasion(0.0).withResistance(0.0).build();
            Bot teammate = aBot().withResistance(0.0).build();
            Bot immuneTeammate = aBot().withResistance(1.0).build();

            Player opponent = aPlayer().withTeam(target, teammate, immuneTeammate).build();
            opponent.getTeam().forEach(b -> b.setOwner(opponent));

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
