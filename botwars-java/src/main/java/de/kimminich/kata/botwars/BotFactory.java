package de.kimminich.kata.botwars;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static de.kimminich.kata.botwars.BotTypes.AGGRO_BOT;
import static de.kimminich.kata.botwars.BotTypes.STEALTH_BOT;
import static de.kimminich.kata.botwars.BotTypes.GLASS_BOT;
import static de.kimminich.kata.botwars.BotTypes.TANK_BOT;
import static de.kimminich.kata.botwars.BotTypes.BEAVERETTE_BOT;
import static de.kimminich.kata.botwars.BotTypes.KAMIKAZE_BOT;

public final class BotFactory {

    public static Set<Bot> createDefaultRoster() {
        return Arrays.stream(BotTypes.values()).map(BotFactory::create).collect(Collectors.toCollection(HashSet::new));
    }

    private BotFactory() {
    }

    static Bot create(BotTypes type) {
        switch (type) {
            case AGGRO_BOT: return new Bot(AGGRO_BOT.toString(), 100, 20, 40, 800, 0.0, 0.1);
            case STEALTH_BOT: return new Bot(STEALTH_BOT.toString(), 70, 20, 90, 500, 0.2, 0.2);
            case GLASS_BOT: return new Bot(GLASS_BOT.toString(), 180, 0, 20, 300, 0.3, 0.1);
            case TANK_BOT: return new Bot(TANK_BOT.toString(), 50, 40, 30, 1200, 0.05, 0.1);
            case BEAVERETTE_BOT: return new Bot(BEAVERETTE_BOT.toString(), 70, 30, 35, 1000, 0.05, 0.15);
            case KAMIKAZE_BOT: return new Bot(KAMIKAZE_BOT.toString(), 50, 0, 40, 500, 0.0, 0.2);
            default: throw new AssertionError("Unexpected BotType: " + type);
        }
    }

}
