package pg.jee.lab.api.converters;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Circuit;

import javax.ws.rs.ext.Provider;

@Provider
public class CircuitConverter extends AbstractEntityConverter<Circuit> {
    public CircuitConverter() {
        super(Circuit.class, Circuit::getId, ResultService::findCircuit);
    }
}