package de.kimminich.kata.botwars;

import org.junit.gen5.api.Disabled;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertTrue;

public class BotTest {

    @Test
    void calculatesDamagePotentialFromPower() {
        Bot bot = new Bot();
        bot.setPower(100);

        for (int i = 0; i<100000; i++) {
            int damage = bot.calculateDamage(new Bot());
            assertTrue(damage >= 50);
            assertTrue(damage <= 100);
        }
    }

    @Test
    void reduceDamageTakenByArmor() {
        Bot bot = new Bot();
        bot.setPower(100);

        Bot opponent = new Bot();
        opponent.setArmor(10);

        for (int i = 0; i<100000; i++) {
            int damage = bot.calculateDamage(opponent);
            assertTrue(damage >= 40);
            assertTrue(damage <= 90);
        }
    }

}
