package de.kimminich.kata.botwars;

import de.kimminich.kata.botwars.effects.negative.Bomb;
import de.kimminich.kata.botwars.effects.negative.ContinuousDamage;
import de.kimminich.kata.botwars.effects.negative.DefenseDown;
import de.kimminich.kata.botwars.effects.negative.OffenseDown;
import de.kimminich.kata.botwars.effects.negative.SpeedDown;
import de.kimminich.kata.botwars.effects.negative.Stun;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    void unCamelCaseWorksForEffectNames() {
        assertEquals("Bomb", Utils.unCamelCase(Bomb.class.getSimpleName()));
        assertEquals("Continuous Damage", Utils.unCamelCase(ContinuousDamage.class.getSimpleName()));
        assertEquals("Defense Down", Utils.unCamelCase(DefenseDown.class.getSimpleName()));
        assertEquals("Offense Down", Utils.unCamelCase(OffenseDown.class.getSimpleName()));
        assertEquals("Speed Down", Utils.unCamelCase(SpeedDown.class.getSimpleName()));
        assertEquals("Stun", Utils.unCamelCase(Stun.class.getSimpleName()));
    }

}
