package org.elsys.netprog;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    private static DatabaseConnector instance;

    private Connection connect = null;
//    private Statement statement = null;
//    private PreparedStatement preparedStatement = null;
//    private ResultSet resultSet = null;
    private SessionFactory factory;

    private static final String USER = System.getProperty("sqlUser");
    private static final String PASS = System.getProperty("sqlPass");

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

            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

//            ServiceRegistry registry = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties()).build();

            factory = configuration.buildSessionFactory(/*registry*/);

//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
    }

    public String getInfo() throws SQLException {

        Statement statement = connect.createStatement();

        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;")) {

            resultSet.first();

            return resultSet.getString("UserName");

        }
    }

    public void save(Object object) {
        Transaction transaction = null;

        try (Session session = factory.withOptions().openSession()) {
            transaction = session.beginTransaction();

            session.save(object);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

//    private void insertData() throws Exception {
//        preparedStatement = connect.prepareStatement("INSERT INTO Responses (Message) VALUES (?);");
//        preparedStatement.setString(1, "Called " + 1 + " times");
//        preparedStatement.executeUpdate();
//    }
//
//    public String getResponse() throws SQLException {
//        StringBuilder message = new StringBuilder();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connect = DriverManager.getConnection("jdbc:mysql://localhost:8081/cinemaDB",
//                            "cinemaAdmin", "cinema_Pass123");
//            statement = connect.createStatement();
//            statement.executeQuery("CREATE TABLE Responses("+
//                         "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
//                         "Message VARCHAR(50) NOT NULL"+
//                         ");");
//            insertData();
//            resultSet = statement
//                            .executeQuery("SELECT res.Message" +
//                                     " FROM Responses AS res;");
//            resultSet.next();
//            message.append("Call message: ");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//            if (connect != null) {
//                connect.close();
//            }
//        }
//
//        return message.toString();
//    }
    
}
