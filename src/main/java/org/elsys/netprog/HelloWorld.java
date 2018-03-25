package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseConnector;
import org.elsys.netprog.model.User;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
//@Template
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloWorld() {

        DatabaseConnector connector = DatabaseConnector.getInstance();

        User user = new User();
        user.setUserName("test");
        user.setPassword("test");

//
//        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
//
//        SessionFactory sf = cfg.buildSessionFactory();
//
//        Session session = sf.openSession();
//
//        Transaction tx = session.beginTransaction();
//
//        session.save(user);
//
//        tx.commit();

        connector.proccessObject((Session s) -> s.save(user));


        return Response.ok().entity("string").build();
//        return new Viewable("index.jsp", test);
    }
}
