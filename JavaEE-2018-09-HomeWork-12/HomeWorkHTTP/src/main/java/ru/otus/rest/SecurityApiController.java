package ru.otus.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static ru.otus.shared.Constants.Rest.*;


@Path("/v1/security")
public class SecurityApiController
{
    private static final Logger l = LogManager.getLogger(SecurityApiController.class.getName());

    @POST
    @Path(API_PATH_SECURITY_LOGIN + "/{" + PATH_PARAM_USER_NAME + "}/{" + PATH_PARAM_USER_PASS + "}")
    public Response login(
        @Context HttpServletRequest request,
        @NotBlank @PathParam(PATH_PARAM_USER_NAME) String userName,
        @NotBlank @PathParam(PATH_PARAM_USER_PASS) String password)
    {
        try {
            l.info("SecurityApiController.login:: login user " + userName);
            request.login(userName, password);
            return Response.status(200).build();
        }
        catch (ServletException e) {
            l.error("SecurityApiController.login:: error during login user " + userName + "(" + e.getMessage() + ")", e);
        }
        return Response.status(500).build();
    }

    @POST
    @Path(API_PATH_SECURITY_LOGOUT)
    public Response logout(@Context HttpServletRequest request)
    {
        try {
            l.info("SecurityApiController.login:: logout current user");
            request.logout();
            return Response.status(200).build();
        }
        catch (ServletException e) {
            l.error("SecurityApiController.login:: error during logout current user (" + e.getMessage() + ")", e);
        }
        return Response.status(500).build();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
