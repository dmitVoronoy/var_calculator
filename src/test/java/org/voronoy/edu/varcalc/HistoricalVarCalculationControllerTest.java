package org.voronoy.edu.varcalc;

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
    @Client("/")
    private RxHttpClient client;

    @Test
    @DisplayName("Should compute VaR for single value")
    void singleValue() {
        String json = """
        {
            "pnlValues" : [5, 5, 5, 5, 1]
        }
        """ ;

        double var = client.toBlocking().retrieve(POST("/var/99", json), Double.class);
        assertThat(var).isEqualTo(5.0);
    }

}
