package org.voronoy.edu.varcalc.controller;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.micronaut.http.HttpRequest.POST;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class HistoricalVarCalculationControllerTest {

    @Inject
    @Client("/v1/var")
    private RxHttpClient client;

    @Test
    @DisplayName("Should compute VaR for single value")
    void singleTrade() {
        String json = """
        {
            "pnlValues" : [5, 5, 5, 5, 1]
        }
        """ ;
        String var = client.toBlocking().retrieve(POST("/trade/99", json), String.class);
        assertThat(var.trim()).isEqualTo("1 profit");
    }

    @Test
    @DisplayName("Should compute VaR for portfolio")
    void portfolio() {
        String json = """
        {
            "trade1" : {
                "pnlValues" : [5, 5, 5, 5, 2]
            },
            "trade2" : {
                "pnlValues" : [2, 2, 5, 5, 2]
            }
        }
        """ ;
        String var = client.toBlocking().retrieve(POST("/portfolio/99.5", json), String.class);
        assertThat(var.trim()).isEqualTo("2 profit");
    }

    @Test
    @DisplayName("Should compute loss VaR for portfolio")
    void portfolioLoss() {
        String json = """
        {
            "trade1" : {
                "pnlValues" : [-5.3, -5, -5.3, -5.3, -1]
            },
            "trade2" : {
                "pnlValues" : [1, 1.345, -5.3, -5.3, -1]
            }
        }
        """ ;
        String var = client.toBlocking().retrieve(POST("/portfolio/99.5", json), String.class);
        assertThat(var.trim()).isEqualTo("5.3 loss");
    }

}
