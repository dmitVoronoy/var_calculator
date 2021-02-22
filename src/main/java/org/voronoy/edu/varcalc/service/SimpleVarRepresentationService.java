package org.voronoy.edu.varcalc.service;

import javax.inject.Singleton;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Singleton
public class SimpleVarRepresentationService implements VarRepresentationService<BigDecimal, String> {
    private static final String PATTERN = "%s %s\n";

    @Override
    public String render(BigDecimal value) {
        return switch (value.compareTo(ZERO)) {
            case 1 -> PATTERN.formatted(value, "profit");
            case -1 -> PATTERN.formatted(value.negate(), "loss");
            default -> "Neither profit nor loss\n";
        };
    }
}
