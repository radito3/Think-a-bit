package org.elsys.netprog;

import org.elsys.netprog.db.DatabaseConnector;
import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.QuestionCategories;
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

@Path("/dbtest")
public class DBTest {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDBInfo() {
//        DatabaseConnector conn = DatabaseConnector.getInstance();

        String val = "default";
        String val1 = "default";

        User user = new User();
        user.setId(2);
        user.setUserName("User");
        user.setPassword("Pass");

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Categories.class)
                .addAnnotatedClass(QuestionCategories.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory factory = configuration.buildSessionFactory(registry);


//        User user1 = conn.getObject((Session s) -> s.get(User.class, 1));

//        conn.proccessObject((Session s) -> s.save(user1));

//        try {
//            val = conn.getInfo();

            Session session = factory.openSession();

            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(user);
//            session.delete(user);

            transaction.commit();

//            val1 = conn.getInfo();



//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return Response.ok().entity("first check: " + val + "\nsecond check: " + val1).build();
    }

    @GET
    @Path("/1")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUser() {
//        Configuration configuration = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Question.class)
//                .addAnnotatedClass(Categories.class)
//                .addAnnotatedClass(QuestionCategories.class);
//
//        ServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties()).build();
//
//        SessionFactory factory = configuration.buildSessionFactory(registry);
//
//        Session session = factory.openSession();
//
//        Transaction transaction = session.beginTransaction();

        User user = DatabaseConnector.getInstance().getObject(s -> s.get(User.class, 2));//session.get(User.class, 2);

//        transaction.commit();

        return Response.ok().entity(user.toString()).build();
    }

    @GET
    @Path("/2")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUser() {
        DatabaseConnector.getInstance()
                .processObject(s -> s.save(new User(3, "three", "three")));
        return Response.status(200).entity("nice try").build();
    }

}
