package DataAccess;

import java.sql.*;


/**
 *
 * @author sqlitetutorial.net
 */
public class CreateDB {

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:familymap.sqlite";

        // SQL statement for creating a new table
        String sql = "DROP TABLE IF EXISTS User;\n" +
                "DROP TABLE IF EXISTS Person;\n" +
                "DROP TABLE IF EXISTS Event;\n" +
                "DROP TABLE IF EXISTS Authtoken;\n" +
                "\n" +
                "CREATE TABLE User (\n" +
                "    username varchar(255) not null,\n" +
                "    password varchar(255) not null,\n" +
                "    email varchar(255) not null,\n" +
                "    firstName varchar(255) not null,\n" +
                "    lastName varchar(255) not null,\n" +
                "    gender varchar(255) not null,\n" +
                "    personID varchar(255) not null primary key\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Person (\n" +
                "    personID varchar(255) not null primary key,\n" +
                "    associatedUsername varchar(255) not null,\n" +
                "    firstName varchar(255) not null,\n" +
                "    lastName varchar(255) not null,\n" +
                "    gender varchar(255) not null,\n" +
                "    fatherID varchar(255),\n" +
                "    motherID varchar(255),\n" +
                "    spouseID varchar(255)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Event (\n" +
                "    eventID varchar(255) not null primary key,\n" +
                "    associatedUsername varchar(255) not null,\n" +
                "    personID varchar(255) not null,\n" +
                "    latitude float not null,\n" +
                "    longitude float not null,\n" +
                "    country varchar(255) not null,\n" +
                "    city varchar(255) not null,\n" +
                "    eventType varchar(255) not null,\n" +
                "    year int not null\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Authtoken(\n" +
                "    authtoken varchar(255) not null,\n" +
                "    username varchar(255) not null\n" +
                ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewTable();
    }
}