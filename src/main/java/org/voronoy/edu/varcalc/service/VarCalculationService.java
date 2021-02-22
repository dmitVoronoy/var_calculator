package org.voronoy.edu.varcalc.service;

import java.math.BigDecimal;

public interface VarCalculationService<T> {
    BigDecimal calculate(BigDecimal confidenceLevel, DataProvider<T> dataProvider);

    interface DataProvider<T> {
        T getData();
    }
}
