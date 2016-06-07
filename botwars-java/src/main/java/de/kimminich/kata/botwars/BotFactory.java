package de.kimminich.kata.botwars;

final class BotFactory {

    enum BotTypes {
        AGGRO_BOT, STEALTH_BOT, GLASS_BOT, TANK_BOT, BEAVERETTE_BOT, KAMIKAZE_BOT
    }

    private BotFactory() {

    }

    static Bot create(BotTypes type) {
        switch (type) {
            case AGGRO_BOT: return new Bot("Aggro Bot", 100, 20, 40, 800, 0.0, 0.1);
            case STEALTH_BOT: return new Bot("Stealth Bot", 70, 20, 90, 500, 0.2, 0.2);
            case GLASS_BOT: return new Bot("Glass Bot", 180, 0, 20, 300, 0.3, 0.1);
            case TANK_BOT: return new Bot("Tank Bot", 50, 40, 30, 1200, 0.05, 0.1);
            case BEAVERETTE_BOT: return new Bot("Beaverette Bot", 70, 30, 35, 1000, 0.05, 0.15);
            case KAMIKAZE_BOT: return new Bot("Kamikaze Bot", 50, 0, 40, 500, 0.0, 0.2);
            default: throw new AssertionError("Unexpected BotType: " + type);
        }
    }

}
