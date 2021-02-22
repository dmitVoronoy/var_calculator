package org.voronoy.edu.varcalc.service;

public interface VarRepresentationService<I, O> {
    O render(I value);
}
