package org.voronoy.edu.varcalc.service;


import org.voronoy.edu.varcalc.model.PnlValues;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;
import static java.util.Collections.sort;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Singleton
public class HistoricalVarCalculationService implements VarCalculationService<List<PnlValues>> {

    public static final BigDecimal HUNDRED = new BigDecimal("100");

    @Override
    public BigDecimal calculate(BigDecimal confidenceLevel, DataProvider<List<PnlValues>> dataProvider) {
        checkConfidenceLevel(confidenceLevel);
        requireNonNull(dataProvider);
        List<PnlValues> pnlValues = dataProvider.getData();
        requireNonNull(pnlValues);
        List<BigDecimal> values = flatMapValues(pnlValues);
        sort(values);
        int offset = calculateOffset(confidenceLevel, values);
        return values.get(values.size() - offset);
    }

    private static List<BigDecimal> flatMapValues(List<PnlValues> pnlValues) {
        return pnlValues
                .stream()
                .flatMap(v -> v.getPnlValues().stream())
                .collect(toList());
    }

    private static void checkConfidenceLevel(BigDecimal confidenceLevel) {
        requireNonNull(confidenceLevel);
        if (confidenceLevel.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Confidence level cannot be less than zero");
        }
    }

    private static int calculateOffset(BigDecimal confidenceLevel, List<BigDecimal> values) {
        BigDecimal valuesLength = valueOf(values.size());
        return confidenceLevel
                .multiply(valuesLength)
                .divide(HUNDRED, 0, CEILING)
                .intValue();
    }
}
