package org.elsys.netprog.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseConnector implements Connection {

    private static DatabaseConnector instance;

    private SessionFactory factory;

//    private static final String USER = System.getProperty("sqlUser");
//    private static final String PASS = System.getProperty("sqlPass");

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    private DatabaseConnector() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Think_a_bitDB", USER, PASS);

//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
        factory = Connection.connect();
    }

    @Override
    public void processObject(Consumer<Session> consumer) {
        processObject(consumer, null);
    }

    @Override
    public <T> T getObject(Function<Session, T> function) {
        return processObject(null, function);
    }

    private <T> T processObject(Consumer<Session> consumer, Function<Session, T> function) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            if (function != null) {
                return function.apply(session);
            }
            consumer.accept(session);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return null;
    }
}
