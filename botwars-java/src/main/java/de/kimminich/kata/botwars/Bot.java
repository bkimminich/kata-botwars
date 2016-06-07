package de.kimminich.kata.botwars;

import java.util.Random;

public class Bot {

    private Random random = new Random();

    public Bot(int power, int armor, int speed, int integrity, double evasion) {
        this.power = power;
        this.armor = armor;
        this.speed = speed;
        this.integrity = integrity;
        this.evasion = evasion;
    }

    private Player owner;

    private final int power;
    private final int armor;
    private final int speed;
    private final double evasion;

    private int integrity;
    private int turnMeter = 0;

    public void attack(Bot target) {
        target.takeDamage(random.nextInt(power / 2) + power / 2);
    }

    public void takeDamage(int damage) {
        if (random.nextDouble() > evasion) {
            damage = Math.max(0, damage - armor);
            integrity = Math.max(0, integrity - damage);
        }
    }

    public int getIntegrity() {
        return integrity;
    }

    public boolean isDestroyed() {
        return integrity == 0;
    }

    public int getTurnMeter() {
        return turnMeter;
    }

    public void fillTurnMeter() {
        turnMeter += speed;
    }

    public void resetBot() {
        turnMeter = 0;
    }

    public void depleteTurnMeter() {
        turnMeter -= 1000;
    }

    public boolean canTakeTurn() {
        return turnMeter >= 1000;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
