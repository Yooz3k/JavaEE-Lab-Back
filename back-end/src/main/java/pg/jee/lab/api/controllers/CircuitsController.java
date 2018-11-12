package pg.jee.lab.api.controllers;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Circuit;

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

@Path("/circuits")
public class CircuitsController {

    @Inject
    ResultService resultService;

    @GET
    public Collection<Circuit> getAllCircuits() {
        return resultService.findAllCircuits();
    }

    @GET
    @Path("/{circuit}")
    public Circuit getCircuit(@PathParam("circuit") Circuit circuit) {
        return circuit;
    }

    @POST
    public Response saveCircuit(Circuit circuit) {
        resultService.saveCircuit(circuit);
        return Response.created(uri(CircuitsController.class, "getCircuit", circuit.getId())).build();
    }

    @DELETE
    @Path("/{circuit}")
    public Response deleteCircuit(@PathParam("circuit") Circuit circuit) {
        resultService.removeCircuit(circuit);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{circuit}")
    public Response updateCircuit(@PathParam("circuit") Circuit originalCircuit, Circuit updatedCircuit) {
        if (!originalCircuit.getId().equals(updatedCircuit.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        resultService.saveCircuit(updatedCircuit);
        return Response.ok().build();
    }
}
