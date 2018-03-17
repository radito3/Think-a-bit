package org.elsys.netprog;

import java.sql.*;

public class DatabaseConnector {

    private static DatabaseConnector instance;

    private Connection connect = null;
    private Statement statement = null;
//    private PreparedStatement preparedStatement = null;
//    private ResultSet resultSet = null;

    private static final String USER = System.getProperty("sqlUser");
    private static final String PASS = System.getProperty("sqlPass");

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    private DatabaseConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Think-a-bit", USER, PASS);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return USER;
    }

    public String getPass() {
        return PASS;
    }

    public String getInfo() throws SQLException {

        statement = connect.createStatement(); //blows up here

        statement.executeQuery("USE DATABASE Think_a_bitDB");

        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;")) {
            return resultSet.getString(1);
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
