package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("WebServiceTest_war/myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getForm() {
        return "<html><body><form action=\"myresource\" method=\"get\">" +
                "<label for=\"name\">Enter your name:</label>" +
                "<input type=\"text\" id=\"name\" name=\"name\">" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form></body></html>";
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public String getIt(@PathParam("name") String name) {
        return "<html><body><p>Hello, " + name + "!</p></body></html>";
    }
}