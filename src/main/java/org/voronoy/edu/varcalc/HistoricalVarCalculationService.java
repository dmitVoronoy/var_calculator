package org.voronoy.edu.varcalc;

import javax.inject.Singleton;

import static java.lang.Math.round;
import static java.util.Arrays.sort;

@Singleton
public class HistoricalVarCalculationService implements VarCalculationService<PnlValues> {
    @Override
    public double calculate(double confidenceLevel, DataProvider<PnlValues> dataProvider) {
        checkConfidenceLevel(confidenceLevel);
        PnlValues pnlValues = dataProvider.getData();
        double[] values = pnlValues.getPnlValues();
        sort(values);
        int index = calculateIndex(confidenceLevel, values);
        return values[index - 1];
    }

    private void checkConfidenceLevel(double confidenceLevel) {
        if (confidenceLevel < 0) {
            throw new IllegalArgumentException("Confidence level cannot be less than zero");
        }
    }

    private int calculateIndex(double confidenceLevel, double[] values) {
        return (int) round(confidenceLevel / 100 * values.length);
    }
}
