package org.elsys.netprog;

import org.elsys.netprog.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Path("/test")
public class HelloWorld {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld(User user) {

        return Response.ok().entity(user.toString()).build();
    }

    @GET
    @Path("/1")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL("http://localhost:8080/Think-a-bit/test");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<user>\n" +
                    "    <id>1</id>\n" +
                    "    <username>test</username>\n" +
                    "    <password>testp</password>\n" +
                    "</user>";

            byte[] value = str.getBytes(StandardCharsets.UTF_8);

            connection.setFixedLengthStreamingMode(value.length);
            connection.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");

            connection.connect();

            try (OutputStream os = connection.getOutputStream()) {
                os.write(value);
            }

            InputStream is = connection.getInputStream();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line).append('\n');
                }
            }
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return Response.ok().entity(response.toString()).build();
    }
}
