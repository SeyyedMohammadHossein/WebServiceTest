package Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection {
    private java.sql.Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public SqlConnection(String address, String username, String password) {
        try {
            connection = DriverManager.getConnection(address, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getResultSet(String tableName) {
        try {
            resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUserToDatabase(String name, String email, String password) {
        try {
            statement.executeUpdate("INSERT INTO users (name,email,password) VALUES ('" + name + "','" + email + "','" + password + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBookToDatabase(String ISBN, String name, String author, String hasReserved) {
        try {
            statement.executeUpdate("INSERT INTO books (ISBN,name,author,hasReserved) VALUES ('" + ISBN + "','" + name + "','" + author + "','" + hasReserved + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBorrowedBookToDatabase(String ISBN, int id) {
        try {
            statement.executeUpdate("UPDATE books SET hasReserved='" + id +"' WHERE ISBN='" + ISBN + "'");
            statement.executeUpdate("INSERT INTO borrowedbooks (ISBN,userID) VALUES ('" + ISBN + "','" + id + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnBookUpdateHasReservedToDatabase(String ISBN){
        try {
            statement.executeUpdate("UPDATE books SET hasReserved='0' WHERE ISBN='"+ ISBN + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBookFromBorrowedBooksList(String ISBN, int id){
        try {
            statement.executeUpdate("DELETE FROM borrowedbooks WHERE ISBN='"+ ISBN +"' AND userID='" + id + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
