package com.company;

import java.sql.*;

public class Main {

    /*
     *  Class serves to work with tables
     */

    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/usersDB";
    static final String login = "postgres";
    static final String pass = "root";

    static final String createtable = "CREATE TABLE users ("
            + "id SERIAL, "
            + "username VARCHAR(20) NOT NULL, "
            + "createdby VARCHAR(20) NOT NULL, "
            + "createddate DATE NOT NULL, "
            + "PRIMARY KEY(id))";

    static final String droptable = "DROP TABLE users";

    static final String insertnewuser = "INSERT INTO users"
            + "(username, createdby, createddate) " + "VALUES "
            + "('Darth Vader', 'consoleDB', '2021/02/18'), "
            + "('Obi Wan Kenobi', 'consoleDB', '2021/03/01');";

    static final String selectall = "SELECT * FROM users;";

    public static void main(String[] args) {
        System.out.append("Testing postgreSQL JDBC: ");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            e.printStackTrace();
            return;
        }

        System.out.println("Driver is connected!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, login, pass);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Connected success!");
        } else {
            System.out.println("Failed to connect!");
        }
        System.out.println("---------------");

        Statement statement = null;

        /*
         * DROP TABLE
         */

        try {
            System.out.println("Drop table 'users'");
            statement = connection.createStatement();
            statement.execute(droptable);
        }
        catch (SQLException e) {
            System.out.println("Table is not dropped!");
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e) {
                System.out.println("Statement is not closed");
            }
        }
        System.out.println("Table is dropped!");
        System.out.println("---------------");

        /*
         * CREATE NEW TABLE
         */

        try {
            System.out.println("Creating new table 'users'");
            statement = connection.createStatement();
            statement.execute(createtable);
        }
        catch (SQLException e) {
            System.out.println("Table is not created!");
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e) {
                System.out.println("Statement is not closed");
            }
        }
        System.out.println("Table is created!");
        System.out.println("---------------");

        /*
         * CREATE NEW USER
         */

        try {
            System.out.println("Adding new users");
            statement = connection.createStatement();
            statement.execute(insertnewuser);
        }
        catch (SQLException e) {
            System.out.println("New data is not imported");
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e) {
                System.out.println("Statement is not closed");
            }
        }
        System.out.println("New users added");
        System.out.println("---------------");

        try {
            System.out.println("Retrieving data on users");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectall);
            while (rs.next()) {
                System.out.printf("id: %s \t username: %s \t createdby: %s \t createddate: %s \n",
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("createdby"),
                        rs.getString("createddate"));
            }
        }
        catch (SQLException e) {
            System.out.println("Data is not read correctly!");
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e) {
                System.out.println("Statement is not closed");
            }
        }
        System.out.println("---------------");

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Unable to close the connection");
            e.printStackTrace();
            return;
        }
        System.out.println("Connection is closed!");
    }
}
