package pg.jee.lab.api.converters;

import pg.jee.lab.business.ResultService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractEntityConverter<V> implements ParamConverterProvider {

    @Inject
    ResultService resultService;

    Class<V> entityClass;

    private BiFunction<ResultService, Integer, V> retrieveFunction;

    private Function<V, Integer> idExtractor;

    AbstractEntityConverter(Class<V> entityClass, Function<V, Integer> idExtractor, BiFunction<ResultService, Integer, V> retrieveFunction) {
        this.entityClass = entityClass;
        this.retrieveFunction = retrieveFunction;
        this.idExtractor = idExtractor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType != entityClass) {
            return null;
        }

        return (ParamConverter<T>) new ParamConverter<V>() {
            @Override
            public V fromString(String value) {
                V entity = retrieveFunction.apply(resultService, Integer.valueOf(value));

                if (entity == null) {
                    throw new NotFoundException();
                }

                return entity;
            }

            @Override
            public String toString(V result) {
                return idExtractor.apply(result).toString();
            }
        };
    }
}
