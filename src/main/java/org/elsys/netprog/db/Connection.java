package org.elsys.netprog.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Connection {

    static SessionFactory connect() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(registry);
    }

    <T> T proccessObject(Consumer<Session> consumer, Function<Session, T>... functions);
}
