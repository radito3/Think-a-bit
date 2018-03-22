package org.elsys.netprog;

import org.elsys.netprog.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/dbtest")
public class DBTest {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDBInfo() {
        DatabaseConnector conn = DatabaseConnector.getInstance();

        String val = "default";
        String val1 = "default";

        User user = new User();
        user.setId(2);
        user.setUserName("User");
        user.setPassword("Pass");

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory factory = configuration.buildSessionFactory(registry);

        try {
            val = conn.getUserInfo();

            Session session = factory.openSession();

            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(user);
//            session.delete(user);

            transaction.commit();

            val1 = conn.getUserInfo();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok().entity("first check: " + val + "\nsecond check: " + val1).build();
    }

    @GET
    @Path("/1")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUser() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory factory = configuration.buildSessionFactory(registry);

        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, 1);

        transaction.commit();

        return Response.ok().entity(user.toString()).build();
    }
}
