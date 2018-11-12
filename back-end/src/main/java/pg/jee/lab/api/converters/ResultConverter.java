package pg.jee.lab.api.converters;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Result;

import javax.ws.rs.ext.Provider;

@Provider
public class ResultConverter extends AbstractEntityConverter<Result> {
    public ResultConverter() {
        super(Result.class, Result::getId, ResultService::findResult);
    }
}
