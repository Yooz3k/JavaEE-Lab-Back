package pg.jee.lab.api.controllers;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Result;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static pg.jee.lab.api.UriUtils.uri;

@Path("/results")
public class ResultsController {

    private final static String RESULT_PATH_PARAM = "result";
    private final static String GET_RESULT_METHOD_NAME = "getResult";

    @Inject
    ResultService resultService;

    @GET
    public Collection<Result> getAllResults() {
        return resultService.findAllResults();
    }

    @GET
    @Path("/{result}")
    public Result getResult(@PathParam(RESULT_PATH_PARAM) Result result) {
        return result;
    }

    @POST
    public Response saveResult(Result result) {
        resultService.saveResult(result);
        return Response.created(uri(ResultsController.class, GET_RESULT_METHOD_NAME, result.getId())).build();
    }

    @DELETE
    @Path("/{result}")
    public Response deleteResult(@PathParam(RESULT_PATH_PARAM) Result result) {
        resultService.removeResult(result);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{result}")
    public Response updateResult(@PathParam(RESULT_PATH_PARAM) Result originalResult, Result updatedResult) {
        if (!originalResult.getId().equals(updatedResult.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        resultService.saveResult(updatedResult);
        return Response.ok(uri(ResultsController.class, GET_RESULT_METHOD_NAME, updatedResult.getId())).build();
    }
}