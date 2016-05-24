package de.kimminich.kata.botwars;

import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;

public class BotTest {

    private Bot bot = new Bot();
    private Bot opponent = new Bot();

    @Test
    void damageTakenIsReducedByArmor() {
        bot.setIntegrity(100);
        bot.setArmor(10);

        bot.takeDamage(20);
        assertEquals(90, bot.getIntegrity());

        bot.takeDamage(60);
        assertEquals(40, bot.getIntegrity());

        bot.takeDamage(5);
        assertEquals(40, bot.getIntegrity(), "Armor will not reduce damage below zero");

    }

    @Test
    void randomDamageIsBetweenHalfAndFullPowerOfAttacker() {
        bot.setPower(100);
        opponent.setArmor(0);

        for (int i = 0; i<100000; i++) {
            opponent.setIntegrity(200);
            bot.causeDamage(opponent);
            assertTrue(opponent.getIntegrity() <= 150);
            assertTrue(opponent.getIntegrity() >= 100);
        }
    }

}
