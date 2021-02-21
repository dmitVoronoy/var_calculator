package org.voronoy.edu.varcalc;

import io.micronaut.http.annotation.*;

import static io.micronaut.http.MediaType.TEXT_JSON;

@Controller("/var")
public class HistoricalVarCalculationController {
    VarCalculationService<PnlValues> varCalculationService;

    HistoricalVarCalculationController(VarCalculationService<PnlValues> varCalculationService) {
        this.varCalculationService = varCalculationService;
    }

    @Post("/{confidenceLevel}")
    @Produces(TEXT_JSON)
    public double calculate(@PathVariable double confidenceLevel, @Body PnlValues pnlValues) {
        return varCalculationService.calculate(confidenceLevel, () -> pnlValues);
    }
}
