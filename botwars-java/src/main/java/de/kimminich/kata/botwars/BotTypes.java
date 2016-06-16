package de.kimminich.kata.botwars;

public enum BotTypes {
    AGGRO_BOT("Aggro Bot"),
    STEALTH_BOT("Stealth Bot"),
    GLASS_BOT("Glass Bot"),
    TANK_BOT("Tank Bot"),
    BEAVERETTE_BOT("Beaverette Bot"),
    KAMIKAZE_BOT("Kamikaze Bot");

    private final String botName;

    BotTypes(String botName) {
        this.botName = botName;
    }

    @Override
    public String toString() {
        return botName;
    }
}
