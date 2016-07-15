package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.extensions.MockitoExtension;
import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.Game;
import de.kimminich.kata.botwars.effects.Effect;
import de.kimminich.kata.botwars.ui.UserInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Optional;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.builders.PlayerBuilder.aPlayer;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("A Continuous Damage effect")
public class ContinuousDamageTest {

    @Test
    @DisplayName("causes damage each turn while the effect is active")
    void causesDamageEachTurn() {
        Effect continuousDamage = createEffectFactoryFor(anyBot(),
                2, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(100).withArmor(0).withStatusEffects(continuousDamage).build();

        target.applyEffects();
        int integrityAfterFirstMove = target.getIntegrity();
        assertTrue(integrityAfterFirstMove < 100);
        target.expireEffects();

        target.applyEffects();
        int integrityAfterSecondMove = target.getIntegrity();
        assertTrue(integrityAfterSecondMove < integrityAfterFirstMove);
        target.expireEffects();

        assertEquals(0, target.getEffects().size());

    }

    @Test
    @DisplayName("causes fixed damage equal to power of invoking bot ignoring its armor")
    void causesFixedDamageEqualToPowerOfInvoker() {
        Effect continuousDamage = createEffectFactoryFor(
                aBot().withPower(100).build(), 1, ContinuousDamage.class).newInstance();
        Bot target = aBot().withIntegrity(150).withArmor(30).withStatusEffects(continuousDamage).build();

        target.applyEffects();

        assertEquals(50, target.getIntegrity(), "Should have caused 100 damage ignoring the 30 armor");

    }

    @Test
    @DisplayName("that destroys its target will have that bot removed from its team before it can attack")
    void botDestroyedFromContinuousDamageWillBeRemovedBeforeItsAttack(@Mock UserInterface ui) {
        Effect continuousDamage = createEffectFactoryFor(aBot().withPower(9999).build(),
                1, ContinuousDamage.class).newInstance();
        Bot bot = aBot().withIntegrity(1).withStatusEffects(continuousDamage).withPower(100).withSpeed(1000).build();
        Bot target = aBot().withIntegrity(100).build();

        when(ui.selectTarget(eq(bot), anyListOf(Bot.class))).thenReturn(Optional.of(target));

        Game game = new Game(ui, aPlayer().withTeam(bot, anyBot(), anyBot()).build(),
                aPlayer().withTeam(target, anyBot(), anyBot()).build());

        game.turn();

        assertEquals(100, target.getIntegrity(),
                "Bot should have been destroyed by Continuous Damage before is had a chance to attack");

    }


}
