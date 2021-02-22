package org.voronoy.edu.varcalc.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class SimpleVarRepresentationServiceTest {
    @Inject
    SimpleVarRepresentationService varRepresentationService;

    @Test
    @DisplayName("Should render profit value")
    void profit() {
        String rendered = varRepresentationService.render(new BigDecimal("3"));
        assertThat(rendered).isEqualTo("3 profit\n");
    }

    @Test
    @DisplayName("Should render loss value")
    void loss() {
        String rendered = varRepresentationService.render(new BigDecimal("-4.3"));
        assertThat(rendered).isEqualTo("4.3 loss\n");
    }

    @Test
    @DisplayName("Should render zero value")
    void zero() {
        String rendered = varRepresentationService.render(ZERO);
        assertThat(rendered).isEqualTo("Neither profit nor loss\n");
    }
}
