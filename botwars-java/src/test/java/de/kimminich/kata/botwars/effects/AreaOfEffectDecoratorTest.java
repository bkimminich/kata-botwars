package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Player;
import de.kimminich.kata.botwars.effects.negative.Bomb;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDurationAndAoE;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

@DisplayName("Wrapped into AoE")
public class AreaOfEffectDecoratorTest {

    @Test
    @DisplayName("Bomb causes damage to the entire team when it expires")
    void causesDamageToTeamWhenExpiring() {
        StatusEffect bomb = createFactoryForEffectWithDurationAndAoE(anyBot(),
                1, Bomb.class).newInstance();

        Bot target = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).withStatusEffects(bomb).build();
        Bot teammate1 = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).build();
        Bot teammate2 = aBot().withIntegrity(100).withArmor(0).withEvasion(0.0).build();

        Player player = aPlayer().withTeam(target, teammate1, teammate2).build();
        player.getTeam().stream().forEach(bot -> {
            bot.setOwner(player);
        });

        target.preMoveActions();
        assertEquals(100, target.getIntegrity(), "Bomb should not cause damage to target while effect is active");
        assertEquals(100, teammate1.getIntegrity(), "Bomb should not cause damage to teammate while effect is active");
        assertEquals(100, teammate2.getIntegrity(), "Bomb should not cause damage to teammate while effect is active");
        target.postMoveActions();

        assertTrue(target.getIntegrity() < 100, "Bomb should causes damage to target when the effect expired");
        assertTrue(teammate1.getIntegrity() < 100, "Bomb should causes damage to teammate 1 when the effect expired");
        assertTrue(teammate2.getIntegrity() < 100, "Bomb should causes damage to teammate 2 when the effect expired");

        assertEquals(0, target.getStatusEffects().size());

    }

}
