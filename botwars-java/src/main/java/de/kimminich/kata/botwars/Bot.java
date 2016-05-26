package de.kimminich.kata.botwars;

import java.util.Random;

public class Bot {

    private Random random = new Random();

    public Bot(int power, int armor, int integrity) {
        this.power = power;
        this.armor = armor;
        this.integrity = integrity;
    }

    private final int power;
    private final int armor;
    private int integrity;

    public void causeDamage(Bot target) {
        target.takeDamage(random.nextInt(power / 2) + power / 2);
    }

    public void takeDamage(int damage) {
        damage = Math.max(0, damage - armor);
        integrity = Math.max(0, integrity - damage);
    }

    public int getIntegrity() {
        return integrity;
    }

    public boolean isDestroyed() {
        return integrity == 0;
    }
}
