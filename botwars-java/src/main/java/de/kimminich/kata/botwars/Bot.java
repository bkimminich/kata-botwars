package de.kimminich.kata.botwars;

import java.util.Random;

public class Bot {

    Random random = new Random();

    private int power;
    private int armor;

    public int calculateDamage(Bot target) {
        return random.nextInt(power/2)+power/2 - target.getArmor();
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }
}
