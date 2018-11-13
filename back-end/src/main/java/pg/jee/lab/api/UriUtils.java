package pg.jee.lab.api;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class UriUtils {
    public static URI uri(Class<?> clazz, String method, Object... vals) {
        return UriBuilder.fromResource(clazz)
                .path(clazz, method)
                .build(vals);
    }

    public static URI uriWithParams(Class<?> clazz, String method, String limit, String offset) {
        URI uri = uri(clazz, method);
        return UriBuilder.fromUri(uri.toString())
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .build();
    }
}
