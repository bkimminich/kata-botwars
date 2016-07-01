package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.StatusEffect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.StatusEffectFactory.createFactoryForEffectWithDuration;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("The Defense Down status effect")
public class DefenseDownTest {

    @Test
    @DisplayName("reduces armor by 50% during its duration")
    void reducesArmorBy50Percent() {
        StatusEffect effect = createFactoryForEffectWithDuration(anyBot(),
                1, DefenseDown.class).newInstance();
        Bot bot = aBot().withArmor(10).withStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(5, bot.getArmor());
        bot.postMoveActions();
        assertEquals(10, bot.getArmor(), "Armor should have been restored");
    }

    @Test
    @DisplayName("reduces resistance by 50% during its duration")
    void reducesResistanceBy50Percent() {
        StatusEffect effect = createFactoryForEffectWithDuration(anyBot(),
                1, DefenseDown.class).newInstance();
        Bot bot = aBot().withResistance(0.1).withStatusEffects(effect).build();

        bot.preMoveActions();
        assertEquals(0.05, bot.getResistance());
        bot.postMoveActions();
        assertEquals(0.1, bot.getResistance(), "Resistance should have been restored after effect expired");
    }

}
