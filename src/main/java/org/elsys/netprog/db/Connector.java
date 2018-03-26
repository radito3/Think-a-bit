package org.elsys.netprog.db;

import org.elsys.netprog.model.Categories;
import org.elsys.netprog.model.Question;
import org.elsys.netprog.model.QuestionCategories;
import org.elsys.netprog.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Connector {

    /**
     * Builds  the {@link org.hibernate.SessionFactory} with the metadata from
     * the entity classes.
     *
     * @return The built SessionFactory
     */
    static SessionFactory connect() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Categories.class)
                .addAnnotatedClass(QuestionCategories.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(registry);
    }

    /**
     * Process an object within the given lambda expression.
     * Used for saving, updating and deleting objects from the database.
     *
     * @param consumer The lambda expression with the needed operation
     */
    void processObject(Consumer<Session> consumer);

    /**
     * Get an object from the database.
     *
     * @param function The lambda expression with the operation needed
     * @param <T> The object needed to be retrieved from the DB
     * @return The wanted object
     */
    <T> T getObject(Function<Session, T> function);
}
