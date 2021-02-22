package org.voronoy.edu.varcalc.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import org.voronoy.edu.varcalc.model.PnlValues;
import org.voronoy.edu.varcalc.service.VarCalculationService;
import org.voronoy.edu.varcalc.service.VarCalculationService.DataProvider;
import org.voronoy.edu.varcalc.service.VarRepresentationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Controller("/v1/var")
public class HistoricalVarCalculationController {
    final VarCalculationService<List<PnlValues>> varCalculationService;
    final VarRepresentationService<BigDecimal, String> varRepresentationService;

    HistoricalVarCalculationController(VarCalculationService<List<PnlValues>> varCalculationService,
                                       VarRepresentationService<BigDecimal, String> varRepresentationService) {
        this.varCalculationService = varCalculationService;
        this.varRepresentationService = varRepresentationService;
    }

    @Post("/trade/{confidenceLevel}")
    public String calculateTrade(@PathVariable BigDecimal confidenceLevel, @Body PnlValues pnlValues) {
        BigDecimal var = varCalculationService.calculate(confidenceLevel, prepareBody(pnlValues));
        return varRepresentationService.render(var);
    }

    @Post("/portfolio/{confidenceLevel}")
    public String calculatePortfolio(@PathVariable BigDecimal confidenceLevel, @Body Map<String, PnlValues> pnlValues) {
        BigDecimal var = varCalculationService.calculate(confidenceLevel, prepareBody(pnlValues));
        return varRepresentationService.render(var);
    }

    private static DataProvider<List<PnlValues>> prepareBody(PnlValues pnlValues) {
        return () -> singletonList(pnlValues);
    }

    private static DataProvider<List<PnlValues>> prepareBody(Map<String, PnlValues> pnlValues) {
        return () -> new ArrayList<>(pnlValues.values());
    }
}
