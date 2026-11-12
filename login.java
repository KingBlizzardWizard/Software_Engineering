
// imports libraries for gui, login and database
import org.mindrot.jbcrypt.BCrypt;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class login {

    // tells java where the SQLite database is located
    static String DB_URL = "jdbc:sqlite:userdata.db";

    public static void main(String[] args) {
        // opens the login window
        SwingUtilities.invokeLater(login::createWindow);
    }

    // creates and displays the login window
    public static void createWindow() {

        // creates the main window
        JFrame frame = new JFrame("Login Menu");

        // closes the program when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // sets the window dimensions
        frame.setSize(380, 190);

        // centers the window
        frame.setLocationRelativeTo(null);

        // creates a grid with 3 rows, 2 columns and spacing between components
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // adds padding around the inside of the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // creates the user input textbox
        JTextField usernameField = new JTextField();

        // creates a password textbox that hides the typed characters
        JPasswordField passwordField = new JPasswordField();

        // creates the register button
        JButton registerButton = new JButton("Register");

        // creates the login button
        JButton loginButton = new JButton("Login");

        // adds the username label and input box to the panel
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        // adds the password label and input box
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        // adds both buttons to the panel
        panel.add(registerButton);
        panel.add(loginButton);

        // runs when the user clicks register
        registerButton.addActionListener(e -> {

            // gets the username and removes spaces from the beginning and end
            String username = usernameField.getText().trim();

            // gets the password from the password field
            String password = new String(passwordField.getPassword());

            // checks if the fields are empty
            if (username.isEmpty() || password.isEmpty()) {

                // displays a warning message
                JOptionPane.showMessageDialog(frame,
                    "Enter a username and password.");

                // stops the registration if one of the fields is empty
                return;
            }

            // connects to the SQLite database
            // try with resources automatically closes the connection
            try (Connection con = DriverManager.getConnection(DB_URL)) {

                // SQL query to check whether the name already exists
                String checkSql =
                    "SELECT 1 FROM player WHERE name = ?";

                // prepares the query so the username can be inserted
                try (PreparedStatement check =
                         con.prepareStatement(checkSql)) {

                    // replaces the ? in the query with the username
                    check.setString(1, username);

                    // executes the query and stores the results
                    try (ResultSet rs = check.executeQuery()) {

                        // if a row exists, then that username is already taken
                        if (rs.next()) {

                            // tells the user that the name is already taken
                            JOptionPane.showMessageDialog(frame,
                                "That username is already taken.");

                            // stops the registration process
                            return;
                        }
                    }
                }

                // hashes the password before storing it in the database
                String hashedPassword = hashPassword(password);

                // SQL query to insert a new player into the database
                String sql =
                    "INSERT INTO player (name, password) VALUES (?, ?)";

                // prepares the insert statement
                try (PreparedStatement stmt =
                         con.prepareStatement(sql)) {

                    // replaces the first ? with the username
                    stmt.setString(1, username);

                    // replaces the second ? with the hashed password
                    stmt.setString(2, hashedPassword);

                    // executes the insert statement to save the new account
                    stmt.executeUpdate();
                }

                // confirms the account was created
                JOptionPane.showMessageDialog(frame,
                    "Account created successfully!");

                // clears the password field after registration
                passwordField.setText("");

            } catch (SQLException ex) {

                // displays an error if the database operation fails
                JOptionPane.showMessageDialog(frame,
                    "Registration failed: " + ex.getMessage());
            }
        });

        // runs when the user clicks login
        loginButton.addActionListener(e -> {

            // gets the username and removes surrounding spaces
            String username = usernameField.getText().trim();

            // gets the password entered by the user
            String password = new String(passwordField.getPassword());

            // searches for a player with a matching name
            // if found, returns the player ID and stored password hash
            String sql =
                "SELECT player_id, password FROM player WHERE name = ?";

            // opens the database connection and prepares the query
            try (Connection con = DriverManager.getConnection(DB_URL);
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                // replaces the first ? with the username
                stmt.setString(1, username);

                // runs the query and stores the matching results
                try (ResultSet rs = stmt.executeQuery()) {

                    // checks whether the username exists
                    if (rs.next()) {

                        // retrieves the hashed password from the database
                        String storedHash = rs.getString("password");

                        // checks whether the entered password matches the stored hash
                        if (verifyPassword(password, storedHash)) {

                            // displays a welcome message and the player's ID
                            JOptionPane.showMessageDialog(frame,
                                "Login successful! Welcome, " + username
                                + "!\nPlayer ID: " + rs.getInt("player_id"));

                            // initializes the player's default profile
                            Main.initializeDefaultProfile(username);

                            // closes the login window
                            frame.dispose();

                        } else {

                            // password does not match
                            JOptionPane.showMessageDialog(frame,
                                "Incorrect username or password.");
                        }

                    } else {

                        // username does not exist
                        JOptionPane.showMessageDialog(frame,
                            "Incorrect username or password.");
                    }
                }

            } catch (SQLException ex) {

                // displays an error if the database operation fails
                JOptionPane.showMessageDialog(frame,
                    "Login failed: " + ex.getMessage());
            }
        });

        // places the panel inside the main window
        frame.add(panel);

        // makes the window visible to the user
        frame.setVisible(true);
    }

    // hashes the password before it is stored in the database
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // checks whether the entered password matches the stored hash
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
