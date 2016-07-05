package de.kimminich.kata.botwars;

import org.modeshape.common.text.Inflector;

public final class Utils {

    private Utils() {
    }

    private static final Inflector INFLECTOR = Inflector.getInstance();

    public static String unCamelCase(String s) {
        return INFLECTOR.titleCase(INFLECTOR.humanize(INFLECTOR.underscore(s)));
    }
}
