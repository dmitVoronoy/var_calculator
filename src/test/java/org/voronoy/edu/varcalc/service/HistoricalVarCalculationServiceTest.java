package org.voronoy.edu.varcalc.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.voronoy.edu.varcalc.model.PnlValues;
import org.voronoy.edu.varcalc.service.VarCalculationService.DataProvider;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class HistoricalVarCalculationServiceTest {
    @Inject
    HistoricalVarCalculationService varCalculationService;

    @Test
    @DisplayName("Should calculate for simple case with positive values")
    void simplePositive() {
        BigDecimal var = varCalculationService
                .calculate(bigDecimal("50.0"), pnlValuesList(pnlValues("1.0", "1.0", "1.0", "1.0", "3.0")));
        assertThat(var).isEqualTo(bigDecimal("1.0"));
    }


    @Test
    @DisplayName("Should calculate for simple case with 99 decimal confidence level")
    void simpleDecimal99() {
        BigDecimal var = varCalculationService
                .calculate(bigDecimal("99.0"),
                        pnlValuesList(pnlValues("1", "2", "3", "4", "5", "5", "5", "5", "5", "5")));
        assertThat(var).isEqualTo(bigDecimal("1"));
    }

    @Test
    @DisplayName("Should calculate for simple case with 99 integer confidence level")
    void simpleInteger99() {
        BigDecimal var = varCalculationService
                .calculate(bigDecimal("99"),
                        pnlValuesList(pnlValues("1", "2", "3", "4", "5", "5", "5", "5", "5", "5")));
        assertThat(var).isEqualTo(bigDecimal("1"));
    }

    @Test
    @DisplayName("Should calculate for simple case with negative values")
    void simpleWithNegative() {
        BigDecimal var = varCalculationService
                .calculate(bigDecimal("99.0"),
                        pnlValuesList(pnlValues("-9", "1", "-1", "1", "1", "0", "1.5", "10.2")));
        assertThat(var).isEqualTo(bigDecimal("-9"));
    }

    @Test
    @DisplayName("Should calculate for simple case with low confidence level")
    void simpleWithLowConfidenceLevel() {
        BigDecimal var = varCalculationService.calculate(bigDecimal("1"),
                pnlValuesList(pnlValues("-9", "-1", "-1", "-3", "1", "-1.4", "-1.5", "-10.2", "-4", "-5", "-1", "-1")));
        assertThat(var).isEqualTo(bigDecimal("1"));
    }

    @Test
    @DisplayName("Should throw exception if confidence level is less than zero")
    void negativeConfidenceLevel() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> varCalculationService.calculate(bigDecimal("-1"), pnlValuesList(pnlValues("3", "2", "2"))));
        assertThat(exception.getMessage()).isEqualTo("Confidence level cannot be less than zero");
    }

    @Test
    @DisplayName("Should calculate for the list")
    void list() {
        BigDecimal var = varCalculationService
                .calculate(bigDecimal("99.9"), pnlValuesList(
                        pnlValues("-9", "1", "-1", "1", "1", "0", "1.5", "10.2"),
                        pnlValues("-9", "-1", "-1", "-3", "1", "-1.4", "-1.5", "-10.2", "-4", "-5", "-1", "-1")));
        assertThat(var).isEqualTo(bigDecimal("-10.2"));
    }

    private static BigDecimal bigDecimal(String value) {
        return new BigDecimal(value);
    }

    private static DataProvider<List<PnlValues>> pnlValuesList(PnlValues... values) {
        return () -> asList(values);
    }

    private static PnlValues pnlValues(String... values) {
        List<BigDecimal> pnlValues = stream(values)
                .map(BigDecimal::new)
                .collect(toList());
        return new PnlValues(pnlValues);
    }

}
