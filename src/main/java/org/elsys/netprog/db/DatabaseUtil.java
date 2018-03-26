package org.elsys.netprog.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseUtil implements Connector {

    private static DatabaseUtil instance;

    private SessionFactory factory;

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    private DatabaseUtil() {
        factory = Connector.connect();
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
