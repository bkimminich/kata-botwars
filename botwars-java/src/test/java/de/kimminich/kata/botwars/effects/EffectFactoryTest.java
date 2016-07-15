package de.kimminich.kata.botwars.effects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.kimminich.kata.botwars.builders.BotBuilder.anyBot;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.expectThrows;

@DisplayName("An effect factory")
public class EffectFactoryTest {

    @Test
    @DisplayName("does create non-AoE effects by default")
    void byDefaultNotAoE() {
        assertFalse(EffectFactory.createEffectFactoryFor(anyBot(), 1, NoEffect.class).isAoE(), "Should not be AoE");
    }

    @Test
    @DisplayName("must explicitly be created for AoE effects")
    void explicitCreationForAoE() {
        assertTrue(EffectFactory.createAoEEffectFactoryFor(anyBot(), 1, NoEffect.class).isAoE(), "Should be AoE");
    }

    @Test
    @DisplayName("can be created for an effect implementation with the required constructor")
    void canCreateFactoryIfRequiredConstrcutorIsPresentInEffectClass() {
        EffectFactory factory = EffectFactory.createEffectFactoryFor(anyBot(), 1, EffectWithConstructor.class);
        assertTrue(factory.newInstance() instanceof EffectWithConstructor);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    @DisplayName("cannot be created for an effect implementation without the required constructor")
    void cannotCreateFactoryIfRequiredConstrcutorIsMissingInEffectClass() {
        Throwable exception = expectThrows(AssertionError.class,
                () -> {
                    EffectFactory factory = EffectFactory.createEffectFactoryFor(
                            anyBot(), 1, EffectWithoutConstructor.class);
                    factory.newInstance();
                });

        assertTrue(exception.getMessage().contains(EffectWithoutConstructor.class.getName()));
    }

}
