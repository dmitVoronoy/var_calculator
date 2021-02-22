package org.voronoy.edu.varcalc.model;

import java.math.BigDecimal;
import java.util.List;

public class PnlValues {
    private List<BigDecimal> pnlValues;

    @SuppressWarnings("unused") // used by jackson while parsing input
    public PnlValues() {
    }

    public PnlValues(List<BigDecimal> pnlValues) {
        this.pnlValues = pnlValues;
    }

    public List<BigDecimal> getPnlValues() {
        return pnlValues;
    }
}
