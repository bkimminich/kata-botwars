package de.kimminich.kata.botwars;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.DynamicTest;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.TestFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static de.kimminich.kata.botwars.BotTypes.*;
import static org.junit.gen5.api.Assertions.*;
import static org.junit.gen5.api.DynamicTest.dynamicTest;

@DisplayName("A bot factory")
public class BotFactoryTest {

    @Test
    @DisplayName("can create an Aggro Bot")
    void canCreateAggroBot() {
        Bot aggroBot = BotFactory.create(AGGRO_BOT);

        assertAll(
                () -> assertEquals("Aggro Bot", aggroBot.getName()),
                () -> assertEquals(800, aggroBot.getIntegrity()),
                () -> assertEquals(100, aggroBot.getPower()),
                () -> assertEquals(40, aggroBot.getSpeed()),
                () -> assertEquals(20, aggroBot.getArmor()),
                () -> assertEquals(0.0, aggroBot.getEvasion()),
                () -> assertEquals(0.1, aggroBot.getCriticalHit()),
                () -> assertEquals(0.1, aggroBot.getResistance()),
                () -> assertEquals(0.3, aggroBot.getEffectiveness())
        );
    }

    @Test
    @DisplayName("can create a Stealth Bot")
    void canCreateStealthBot() {
        Bot stealthBot = BotFactory.create(STEALTH_BOT);

        assertAll(
                () -> assertEquals("Stealth Bot", stealthBot.getName()),
                () -> assertEquals(500, stealthBot.getIntegrity()),
                () -> assertEquals(70, stealthBot.getPower()),
                () -> assertEquals(90, stealthBot.getSpeed()),
                () -> assertEquals(20, stealthBot.getArmor()),
                () -> assertEquals(0.2, stealthBot.getEvasion()),
                () -> assertEquals(0.2, stealthBot.getCriticalHit()),
                () -> assertEquals(0.0, stealthBot.getResistance()),
                () -> assertEquals(0.4, stealthBot.getEffectiveness())
        );
    }

    @Test
    @DisplayName("can create a Glass Bot")
    void canCreateGlassBot() {
        Bot glassBot = BotFactory.create(GLASS_BOT);

        assertAll(
                () -> assertEquals("Glass Bot", glassBot.getName()),
                () -> assertEquals(300, glassBot.getIntegrity()),
                () -> assertEquals(180, glassBot.getPower()),
                () -> assertEquals(20, glassBot.getSpeed()),
                () -> assertEquals(0, glassBot.getArmor()),
                () -> assertEquals(0.3, glassBot.getEvasion()),
                () -> assertEquals(0.1, glassBot.getCriticalHit()),
                () -> assertEquals(0.05, glassBot.getResistance()),
                () -> assertEquals(0.65, glassBot.getEffectiveness())
        );
    }

    @Test
    @DisplayName("can create a Tank Bot")
    void canCreateTankBot() {
        Bot tankBot = BotFactory.create(TANK_BOT);

        assertAll(
                () -> assertEquals("Tank Bot", tankBot.getName()),
                () -> assertEquals(1200, tankBot.getIntegrity()),
                () -> assertEquals(50, tankBot.getPower()),
                () -> assertEquals(30, tankBot.getSpeed()),
                () -> assertEquals(40, tankBot.getArmor()),
                () -> assertEquals(0.05, tankBot.getEvasion()),
                () -> assertEquals(0.1, tankBot.getCriticalHit()),
                () -> assertEquals(0.2, tankBot.getResistance()),
                () -> assertEquals(0.25, tankBot.getEffectiveness())
        );
    }

    @Test
    @DisplayName("can create a Beaverette Bot")
    void canCreateBeaveretteBot() {
        Bot beaveretteBot = BotFactory.create(BEAVERETTE_BOT);

        assertAll(
                () -> assertEquals("Beaverette Bot", beaveretteBot.getName()),
                () -> assertEquals(1000, beaveretteBot.getIntegrity()),
                () -> assertEquals(70, beaveretteBot.getPower()),
                () -> assertEquals(35, beaveretteBot.getSpeed()),
                () -> assertEquals(30, beaveretteBot.getArmor()),
                () -> assertEquals(0.05, beaveretteBot.getEvasion()),
                () -> assertEquals(0.15, beaveretteBot.getCriticalHit()),
                () -> assertEquals(0.1, beaveretteBot.getResistance()),
                () -> assertEquals(0.0, beaveretteBot.getEffectiveness(), "Bot should not inflict any negative effects")
        );
    }

    @Test
    @DisplayName("can create a Kamikaze Bot")
    void canCreateKamikazeBot() {
        Bot kamikazeBot = BotFactory.create(KAMIKAZE_BOT);

        assertAll(
                () -> assertEquals("Kamikaze Bot", kamikazeBot.getName()),
                () -> assertEquals(500, kamikazeBot.getIntegrity()),
                () -> assertEquals(50, kamikazeBot.getPower()),
                () -> assertEquals(40, kamikazeBot.getSpeed()),
                () -> assertEquals(0, kamikazeBot.getArmor()),
                () -> assertEquals(0.0, kamikazeBot.getEvasion()),
                () -> assertEquals(0.2, kamikazeBot.getCriticalHit()),
                () -> assertEquals(0.0, kamikazeBot.getResistance()),
                () -> assertEquals(0.65, kamikazeBot.getEffectiveness())
        );
    }

    @TestFactory
    @DisplayName("can create a default roster")
    Stream<DynamicTest> defaultRosterContainsOneOfEachBotType() {
        Set<Bot> defaultRoster = BotFactory.createDefaultRoster();

        return Arrays.stream(BotTypes.values()).map(botType ->
                dynamicTest("which contains a " + botType, () -> {
                    boolean botInRoster = false;
                    for (Bot bot : defaultRoster) {
                        botInRoster = bot.getName().equals(botType.toString()) || botInRoster;
                    }
                    assertTrue(botInRoster, botType + " should be in default roster.");
                }));
    }

}
