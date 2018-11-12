package pg.jee.lab.api.converters;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Driver;

import javax.ws.rs.ext.Provider;

@Provider
public class DriverConverter extends AbstractEntityConverter<Driver> {
    public DriverConverter() {
        super(Driver.class, Driver::getId, ResultService::findDriver);
    }
}
