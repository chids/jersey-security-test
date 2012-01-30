package org.github.chids.jersey.security.sample;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Resource {

	@GET
	@Path("public")
	@PermitAll
	public String nonRestricted() {
		return "Hello Public World!";
	}

	@GET
	@Path("login")
	@PermitAll
	public Response login(@QueryParam("usr") final String username, @QueryParam("pwd") final String password,
			@Context final HttpServletRequest request, @Context final UriInfo uri) {
		try {
			request.login(username, password);
			request.getSession(true).setAttribute("apa", "kaka");
		} catch (final ServletException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("forbidden").build());
		}
		return Response.temporaryRedirect(
				uri.getBaseUriBuilder().path(getClass()).path(getClass(), "nonRestricted").build()).build();
	}

	@GET
	@Path("private")
	@RolesAllowed("user")
	public String restricted() {
		return "Hello Private World!";
	}
}