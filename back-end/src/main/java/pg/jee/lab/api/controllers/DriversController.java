package pg.jee.lab.api.controllers;

import pg.jee.lab.business.ResultService;
import pg.jee.lab.business.entities.Driver;

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

@Path("/drivers")
public class DriversController {

    @Inject
    ResultService resultService;

    @GET
    public Collection<Driver> getAllDrivers() {
        return resultService.findAllDrivers();
    }

    @GET
    @Path("/{driver}")
    public Driver getDriver(@PathParam("driver") Driver driver) {
        return driver;
    }

    @POST
    public Response saveDriver(Driver driver) {
        resultService.saveDriver(driver);
        return Response.created(uri(DriversController.class, "getDriver", driver.getId())).build();
    }

    @DELETE
    @Path("/{driver}")
    public Response deleteDriver(@PathParam("driver") Driver driver) {
        resultService.removeDriver(driver);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{driver}")
    public Response updateDriver(@PathParam("driver") Driver originalDriver, Driver updatedDriver) {
        if (!originalDriver.getId().equals(updatedDriver.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        resultService.saveDriver(updatedDriver);
        return Response.ok().build();
    }
}