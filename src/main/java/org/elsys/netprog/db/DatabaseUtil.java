package org.elsys.netprog.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class for managing the Database connection and operations with the DB.
 *
 * @author Rangel Ivanov
 */
public class DatabaseUtil implements Connector {

    private static DatabaseUtil instance;

    private SessionFactory factory;

    /**
     * Get the Singleton instance.
     *
     * @return The instance
     */
    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    private DatabaseUtil() {
        factory = Connector.connect();
    }


    /**
     * Save, Update or Delete an object to the Database.
     *
     * @param consumer The lambda expression with the needed operation
     */
    @Override
    public void processObject(Consumer<Session> consumer) {
        processObject(consumer, null);
    }

    /**
     * Get an object from the Database.
     *
     * @param function The lambda expression with the operation needed
     * @param <T> The object needed
     * @return The wanted object
     */
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
