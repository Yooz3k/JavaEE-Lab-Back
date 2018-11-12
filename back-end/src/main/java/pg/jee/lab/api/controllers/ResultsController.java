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

    @Inject
    ResultService resultService;

    @GET
    public Collection<Result> getAllResults() {
        return resultService.findAllResults();
    }

    @GET
    @Path("/{result}")
    public Result getResult(@PathParam("result") Result result) {
        return result;
    }

    @POST
    public Response saveResult(Result result) {
        resultService.saveResult(result);
        return Response.created(uri(ResultsController.class, "getResult", result.getId())).build();
    }

    @DELETE
    @Path("/{result}")
    public Response deleteResult(@PathParam("result") Result result) {
        resultService.removeResult(result);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{result}")
    public Response updateResult(@PathParam("result") Result originalResult, Result updatedResult) {
        if (!originalResult.getId().equals(updatedResult.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        resultService.saveResult(updatedResult);
        return Response.ok().build();
    }
}