package org.voronoy.edu.varcalc;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.voronoy.edu.varcalc.VarCalculationService.DataProvider;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class HistoricalVarCalculationServiceTest {
    @Inject
    HistoricalVarCalculationService varCalculationService;

    @Test
    @DisplayName("Should calculate for simple case with positive values")
    void simplePositive() {
        double var = varCalculationService.calculate(50.0, pnlValues(1.0, 1.0, 1.0, 1.0, 3.0));
        assertThat(var).isEqualTo(1.0);
    }

    @Test
    @DisplayName("Should calculate for simple case with zero values")
    void simpleWithZero() {
        double var = varCalculationService.calculate(99.0, pnlValues(9, 1, 1, 1, 1, 0, 1.5, 10.2));
        assertThat(var).isEqualTo(10.2);
    }

    @Test
    @DisplayName("Should calculate for simple case with negative values")
    void simpleWithNegative() {
        double var = varCalculationService.calculate(95.0, pnlValues(-9, 1, -1, 1, 1, 0, 1.5, 10.2));
        assertThat(var).isEqualTo(10.2);
    }

    @Test
    @DisplayName("Should calculate for simple case with negative result")
    void simpleWithNegativeResult() {
        double var = varCalculationService.calculate(90.0,
                pnlValues(-9, -1, -1, -3, 1, -1.4, -1.5, -10.2, -4, -5, -1, -1));
        assertThat(var).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should throw exception if confidence level is less than zero")
    void negativeConfidenceLevel() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> varCalculationService.calculate(-1, pnlValues(3, 2, 2)));
        assertThat(exception.getMessage()).isEqualTo("Confidence level cannot be less than zero");
    }

    void test() {

    }

    private static DataProvider<PnlValues> pnlValues(double... values) {
        return () -> new PnlValues(values);
    }

}
