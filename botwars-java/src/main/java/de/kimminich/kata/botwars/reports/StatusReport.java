package de.kimminich.kata.botwars.reports;

public class StatusReport {

    private StringBuilder text;

    public StatusReport(String status) {
        text = new StringBuilder(status);
    }

    @Override
    public String toString() {
        return text.toString();
    }

}
