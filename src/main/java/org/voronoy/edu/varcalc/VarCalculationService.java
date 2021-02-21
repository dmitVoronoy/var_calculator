package org.voronoy.edu.varcalc;

public interface VarCalculationService<DATA> {
    double calculate(double confidenceLevel, DataProvider<DATA> dataProvider);

    interface DataProvider<DATA> {
        DATA getData();
    }
}
