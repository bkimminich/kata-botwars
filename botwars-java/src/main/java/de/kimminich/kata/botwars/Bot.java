package de.kimminich.kata.botwars;

import java.util.Random;

public class Bot {

    Random random = new Random();

    private int power;
    private int armor;
    private int integrity;

    public void causeDamage(Bot target) {
        target.takeDamage(random.nextInt(power/2) + power/2);
    }

    public void takeDamage(int damage) {
        integrity -= Math.max(0, damage - armor);
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }

    public int getIntegrity() {
        return integrity;
    }
}
