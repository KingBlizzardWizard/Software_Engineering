
// imports libraries for gui, login and database
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
    //tells java where the SQLite database is located
    static String DB_URL = "jdbc:sqlite:userdata.db";


    public static void main(String[] args) {
        // opens the login window 

        SwingUtilities.invokeLater(login::createWindow);
    }
    // creates and displays the login window
    private static void createWindow() {
        // creates the main window
        JFrame frame = new JFrame("Login Menu");

        //closes the program when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // sets the window dimenstions
        frame.setSize(380, 190);

        // centers the widnow
        frame.setLocationRelativeTo(null);

        // creates a grid with 3 rows, 2 cloumns and spacing between the components 
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // adds padding around the inside of the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // creates the user input textbox
        JTextField usernameField = new JTextField();


        //creates a passwrod textbox that hides the typed char
        JPasswordField passwordField = new JPasswordField();

        //creates the register button
        JButton registerButton = new JButton("Register");

        //creates the login button
        JButton loginButton = new JButton("Login");

        // adds the username lanel and the input box to the panel
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        // adds the password label and the input box 
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        //adds both buttons to the panel
        panel.add(registerButton);
        panel.add(loginButton);

        // runs when the user click register
        registerButton.addActionListener(e -> {
            
            //gets the username and removes spaces from the beginning and end 
            String username = usernameField.getText().trim();

            //gets the passwrod from the password field 

            String password = new String(passwordField.getPassword());

            // checks if the fields are empty 
            if (username.isEmpty() || password.isEmpty()) {
                //displays a warning message 
                JOptionPane.showMessageDialog(frame,
                    "Enter a username and password.");
                    //stops the registation if one of the fields are empty
                return;

            }
            //connects to teh sqllite database
            // try with resources auto closes the connection after 
            try (Connection con = DriverManager.getConnection(DB_URL)) {
               // sql query to check whether the name already exists
                String checkSql =
                    "SELECT 1 FROM player WHERE name = ?";

                // prepares the query so the username can be inserted
                try (PreparedStatement check =
                         con.prepareStatement(checkSql)) {
                    
                            // repalces the ? in the query with teh username
                    check.setString(1, username);

                    // execyters the query and stores the results 
                    try (ResultSet rs = check.executeQuery()) {

                        // if a row exists, then that username is already take 
                        if (rs.next()) {
                            // tells the user that the name is already taken
                            JOptionPane.showMessageDialog(frame,
                                "That username is already taken.");
                                // stops the registratin process
                            return;
                        }
                    }
                }
                // sql query to insert a new player into the database
                String sql =
                    "INSERT INTO player (name, password) VALUES (?, ?)";

                // perpares the insert statement
                try (PreparedStatement stmt =
                         con.prepareStatement(sql)) {
                    //replaces the first ? with the username
                    stmt.setString(1, username);

                    // replaces the second ? with the password
                    stmt.setString(2, password);

                    //executres the insert statement to save the new account
                    stmt.executeUpdate();
                }

                // confirms the account was created
                JOptionPane.showMessageDialog(frame,
                    "Account created successfully!");

                //clears the passwrod field after registration
                passwordField.setText("");
            
            } catch (SQLException ex) {

                //displays an error if the database operation fails
                JOptionPane.showMessageDialog(frame,
                    "Registration failed: " + ex.getMessage());
            }
        });
        // runs when the user clicks login
        loginButton.addActionListener(e -> {

            //gets teh username and removes surrounding spaces 
            String username = usernameField.getText().trim();

            //gets the password etnered by the user
            String password = new String(passwordField.getPassword());

            // seraches for a player with a matching name and password
            // if its found returns the player id 
            String sql =
                "SELECT player_id FROM player WHERE name = ? AND password = ?";

                // opens the database conection and prepares the query
            try (Connection con = DriverManager.getConnection(DB_URL);
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                    // replaces the first ? with the username
                stmt.setString(1, username);

                // replaces the second ? with the password
                stmt.setString(2, password);

                // runs the query and stores the matching results
                try (ResultSet rs = stmt.executeQuery()) {

                    //if a matching account exists then login works
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame,
                            // displays a welome message to the player and their id
                            "Login successful! Welcome, " + username
                            + "!\nPlayer ID: " + rs.getInt("player_id"));
                            Main.initializeDefaultProfile(username); // QUERY SQL FOR PLAYER ID
                            frame.dispose();
                    } else {

                        // no matching account was found
                        JOptionPane.showMessageDialog(frame,
                            "Incorrect username or password.");
                    }
                }

            } catch (SQLException ex) {

                // displays and error if the database operation fails 
                JOptionPane.showMessageDialog(frame,
                    "Login failed: " + ex.getMessage());
            }
        });

        // places the panel inside the main window
        frame.add(panel);

        //makes the window visible to the user 
        frame.setVisible(true);
    }




}
