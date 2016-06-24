package de.kimminich.kata.botwars.effects;

import de.kimminich.kata.botwars.Bot;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.effects.NegativeStatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("The Defense Down negative status effect")
public class DefenseDownTest {

    @Test
    @DisplayName("reduces armor by 50% during its duration")
    void reducesArmorByFiftyPercent() {
        NegativeStatusEffect effect = createFactoryForEffectWithDuration(
                DefenseDown.class, 1).newInstance();
        Bot bot = aBot().withArmor(10).withNegativeStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(5, bot.getArmor());
        bot.postMoveActions();
        assertEquals(10, bot.getArmor(), "Armor should have been restored");
        assertEquals(0, bot.getNegativeStatusEffects().size());

    }

    @Test
    @DisplayName("reduces resistance by 50% during its duration")
    void reducesResistanceByFiftyPercent() {
        NegativeStatusEffect effect = createFactoryForEffectWithDuration(
                DefenseDown.class, 1).newInstance();
        Bot bot = aBot().withResistance(0.1).withNegativeStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(0.05, bot.getResistance());
        bot.postMoveActions();
        assertEquals(0.1, bot.getResistance(), "Resistance should have been restored");
        assertEquals(0, bot.getNegativeStatusEffects().size());

    }

}
