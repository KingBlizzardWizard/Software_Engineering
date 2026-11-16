import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class dbManager {
    private static final String DB_URL = "jdbc:sqlite:userdata.db";

// Takes in a table and a username
// Returns the value of the column for the given username
// query = the column you are trying to access
// currentUser = the user whos table you are trying to access
// table = the table you are trying to access
// e.g. -  getUserData(bio, Matt, 3)


// currently, the ability to access tables is unimplemented, but it should
// be pretty easy to implement it later if its needed.

public static String getUserData(String table, String currentUser, String query){
    //sql query to find profile
        System.out.println("Loading Query:");
        String sqlQuery = "SELECT " + query
                        + " FROM " + table
                        + " WHERE name = '" + currentUser + "'";
        System.out.println(sqlQuery);

        //create connection to database
        try (var conn = DriverManager.getConnection(DB_URL)){
            System.out.println("Connected to SQLite database");
            System.out.println("Query: " + sqlQuery);
            // query database for info
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sqlQuery);
            System.out.println("Retrieved: " + rs.getString(query));
            return rs.getString(query);

        } catch (SQLException e){System.out.println(e.getMessage());
            return "";
    }

}
}