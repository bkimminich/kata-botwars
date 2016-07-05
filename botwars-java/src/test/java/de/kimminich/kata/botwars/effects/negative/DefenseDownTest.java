package de.kimminich.kata.botwars.effects.negative;

import de.kimminich.kata.botwars.Bot;
import de.kimminich.kata.botwars.effects.Effect;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.aBot;
import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static de.kimminich.kata.botwars.effects.EffectFactory.createEffectFactoryFor;
import static org.junit.gen5.api.Assertions.assertEquals;

@DisplayName("The Defense Down effect")
public class DefenseDownTest {

    @Test
    @DisplayName("reduces armor by 50% during its duration")
    void reducesArmorBy50Percent() {
        Effect effect = createEffectFactoryFor(anyBot(),
                1, DefenseDown.class).newInstance();
        Bot bot = aBot().withArmor(10).withStatusEffects(effect).build();

        bot.applyEffects();
        assertEquals(5, bot.getArmor());
        bot.expireEffects();
        assertEquals(10, bot.getArmor(), "Armor should have been restored");
        assertEquals(0, bot.getEffects().size());

    }

    @Test
    @DisplayName("reduces resistance by 50% during its duration")
    void reducesResistanceBy50Percent() {
        Effect effect = createEffectFactoryFor(anyBot(),
                1, DefenseDown.class).newInstance();
        Bot bot = aBot().withResistance(0.1).withStatusEffects(effect).build();

        bot.applyEffects();
        assertEquals(0.05, bot.getResistance());
        bot.expireEffects();
        assertEquals(0.1, bot.getResistance(), "Resistance should have been restored after effect expired");
        assertEquals(0, bot.getEffects().size());

    }

}
