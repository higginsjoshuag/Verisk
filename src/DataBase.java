import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataBase {
    final String dbFile = "verisk.db";
    final String connectionURL = "jdbc:sqlite:" + dbFile;
    private Connection connection;

    // Constructor
    public DataBase() throws SQLException {
        connection = null;
    }

    // Connect to the database
    public boolean connect() throws ClassNotFoundException {
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connection to SQLite has been established.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Disconnect from the database
    public boolean disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    // Create addresses table
    public boolean createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS addresses (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " address text,\n"
                + " city text,\n"
                + " state text,\n"
                + " zip text\n"
                + ");";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Table created successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Delete addresses table
    public boolean deleteTable() {
        String sql = "DROP TABLE IF EXISTS addresses";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Table deleted successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean insert(String address, String city, String state, String zip) {
        String sql = "INSERT INTO addresses(address, city, state, zip) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.setString(2, city);
            if (state == null) {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(3, state);
            }
            if (zip == null) {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(4, zip);
            }
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean clear() {
        String sql = "DELETE FROM addresses";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Table cleared successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public HashMap<String, Integer> query(String sql) throws SQLException {
        HashMap<String, Integer> result = new HashMap<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber-1; i++) {
                result.put(resultSet.getString(i), Integer.parseInt(resultSet.getString(i + 1)));
            }
        }
        return result;
    }
}
