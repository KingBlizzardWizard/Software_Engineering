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


public class Main {
    private static final String DB_URL = "jdbc:sqlite:userdata.db";
    private static final String DB_PostURL = "jdbc:sqlite:posts.db";
    public static String retrievedBio;
    public static String retrievedBirthday;
    public static String retrievedUsername;

    public static void main(String[] args) {
        login.createWindow();
    }


    public static void initializeDefaultProfile(String user_ID){ // function to be run at the beginning of main that creates the empty
        //profile fields to be replaced later

        System.out.println("Loading Query:");
        String sqlQuery = "SELECT Username"
                        + " FROM player"
                        + " WHERE name = '" + user_ID + "'";
        System.out.println(sqlQuery);

        // connect to database and sets username, bio, and birthday strings to the retrieved values
        try (var conn =  DriverManager.getConnection(DB_URL)){ // creates a conn object that connects to the database
            var stmt = conn.createStatement(); // creates a statement that will be able to query the database
            var rs = stmt.executeQuery(sqlQuery); // stores the result of the query
            System.out.println("Connected to SQLite Database");

            // get username
            retrievedUsername = rs.getString("Username"); 
            System.out.println(retrievedUsername);

            // get bio 
            sqlQuery = "SELECT Bio FROM player WHERE name = '" + user_ID + "'";
            stmt.executeQuery(sqlQuery);
            retrievedBio = rs.getString("Bio");
            System.out.println(retrievedBio);

            // get birthday
            sqlQuery = "SELECT Birthday FROM player WHERE name = '" + user_ID + "'";
            stmt.executeQuery(sqlQuery);
            retrievedBirthday = rs.getString("Birthday");
            System.out.println(retrievedBirthday);

        } catch (SQLException e){ System.out.println(e.getMessage());  
        }

        //creates initial JFrame
        JFrame frame = new JFrame("My Profile");
        frame.setSize(1366,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        // top left panel which will have the profile picture and buttons to change profile details or logout
        JPanel profilePanel = new JPanel(); // panel for picture
        JLabel profileText = new JLabel("Profile picture will go here!"); // some text where the picture will be

        profilePanel.add(profileText); // adds the text to the panel

        profilePanel.setPreferredSize(new Dimension(600,300));
        profileText.setPreferredSize(new Dimension(600,300)); // sets a size for the panel so other 
        // elements arent pushed too far out of order

        //adds the new panel to the main frame
        frame.add(profilePanel, BorderLayout.WEST);



         // creates left bottom side which will have picture, name, bio, description
        JPanel descriptionPanel = new JPanel(new GridLayout(0,1)); //creates panel
            JLabel bio = new JLabel();
            JLabel username = new JLabel();
            JLabel birthday = new JLabel();
            bio.setText("No bio set yet!");
            birthday.setText("No birthday set yet!");
            username.setText("No Username set yet!");

            if (retrievedBio != null)
                bio.setText(retrievedBio);
            if (retrievedBirthday != null)
                birthday.setText(retrievedBirthday);
            if (retrievedUsername != null)
                username.setText(retrievedUsername);

        // sets a size for the panel so it does not push the right side panel off of the screen
        descriptionPanel.setPreferredSize(new Dimension(600,100));


        //add labels to panel

        descriptionPanel.add(username);
        descriptionPanel.add(bio);
        descriptionPanel.add(birthday);
        frame.add(descriptionPanel, BorderLayout.WEST); //adds panel to frame




        // creates a toolbar
        JTabbedPane toolbar = new JTabbedPane();
        JPanel friends = new JPanel(); // panels on toolbar
        JPanel posts = new JPanel();
        JPanel settings = new JPanel();

        toolbar.addTab("Posts", posts); //adds the tabs to the toolbar
        toolbar.addTab("Friends",friends);
        toolbar.addTab("Settings", settings);
        posts.add(new JLabel("Posts go here!")); // adds text inside of the tabs
        friends.add(new JLabel("Friends go here!"));

            int j = 1;

            String postSqlQuery = "SELECT PostText"
                                    + " FROM posts"
                                    + " WHERE PostID = '" + j + "'"; 
                                    //The 2 is the PostID, make it read PostID rather than hard coding it.
            System.out.println(postSqlQuery);            



            try(var conn = DriverManager.getConnection(DB_PostURL)){
  
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(postSqlQuery);
                System.out.println("Connected to SQLite Database");
                String retrievedText = rs.getString("PostText"); 
                JLabel text = new JLabel("\n" + retrievedText);//Not making it on new line
                System.out.println(retrievedText);
                posts.add(text);
            } catch (SQLException e1){
                System.out.println(e1.getMessage());
            }



        // buttons for the posts tab 
        JButton postsButton = new JButton("New Post");
        posts.add(postsButton);
        postsButton.addActionListener(e -> {
            System.out.println("Post button clicked");
            try(var conn = DriverManager.getConnection(DB_PostURL)){
                String checkSql =
                    "SELECT 1 FROM post WHERE name = ?";
                    //This is currently the issue, make it actually check the correct db 
                    try (PreparedStatement check =
                         conn.prepareStatement(checkSql)) {
                            check.setString(j, "Post #");
                         }
                    String sql = 
                        "INSERT INTO posts (PostText) VALUES (?)";

                    try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {
                    //replaces the first ? with the username
                    stmt.setString(1, "Post" + j);
                    //executres the insert statement to save the new account
                    stmt.executeUpdate();
                }

                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(postSqlQuery);
                System.out.println("Connected to SQLite Database");
                String retrievedText = rs.getString("PostText"); 
                JLabel text = new JLabel("\n" + retrievedText);//Not making it on new line
                System.out.println(retrievedText);
                posts.add(text);
            } catch (SQLException e1){
                System.out.println(e1.getMessage());
            }
            


        });

        // buttons for the setting tab 
        JButton profileButton = new JButton("Change Profile Picture");
        settings.add(profileButton);
        




        JButton changeUsernameButton = new JButton("Change username");
        settings.add(changeUsernameButton);
        changeUsernameButton.addActionListener( e -> { // creating frame and adding panel for change username
            System.out.println("Change username button clicked");
            JFrame textWindow = new JFrame("Enter new username");
            JPanel textPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            textWindow.add(textPanel);
            textWindow.setSize(600,300);
            textWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            textWindow.setVisible(true);
            JTextField textBox = new JTextField();
            textPanel.add(textBox);

            // add confirm / cancel buttons
            JButton confirmButton = new JButton();
            JButton cancelButton = new JButton();
            confirmButton.setText("Confirm");
            cancelButton.setText("Cancel");
            textPanel.add(confirmButton);
            textPanel.add(cancelButton);

            //functionality for confirm button

            confirmButton.addActionListener(f -> {
                String newUsername = textBox.getText();
                String insertData = "UPDATE player SET Username = ? WHERE name = ?";
                System.out.println("username updated to: " + newUsername);

                try (var conn =  DriverManager.getConnection(DB_URL)){ // creates a conn object that connects to the database
                PreparedStatement pstmt = conn.prepareStatement(insertData); // creates a statement that will be able to query the database
                System.out.println("Connected to SQLite Database");
                pstmt.setString(1, newUsername);
                pstmt.setString(2, user_ID);


                System.out.println("username updated!");
                pstmt.executeUpdate();
                username.setText(newUsername);
                } catch(SQLException l){
                    System.out.println("failed to insert");
                textWindow.dispose();
                }});
            // functionality for cancel button
            cancelButton.addActionListener(d ->{
                textWindow.dispose();
            });
            } // end of confirm button
        );

        JButton changeBirthdayButton = new JButton("Change birthday");
        settings.add(changeBirthdayButton);
        changeBirthdayButton.addActionListener( e -> { // creating frame and adding panel for change Birthday
            System.out.println("Change birthday button clicked");
            JFrame textWindow = new JFrame("Enter new birthday");
            JPanel textPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            textWindow.add(textPanel);
            textWindow.setSize(600,300);
            textWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            textWindow.setVisible(true);
            // JTextField textBox = new JTextField();
            JComboBox monthBox = new JComboBox();
            // monthBox.setPreferredSize(null);
            
            for (int i=0; i<13;i++){
                monthBox.addItem(i);

            }
            textPanel.add(monthBox);
            
            
            JComboBox dayBox = new JComboBox();
            dayBox.setSize(100, 100);
            //It can have fluctuating drop down size depending on which month was selected
            //monthBox.getName
            for (int i=0; i<32;i++){
                dayBox.addItem(i);
 
            }
               textPanel.add(dayBox);
            // add confirm / cancel buttons
            JButton confirmButton = new JButton();
            JButton cancelButton = new JButton();
            confirmButton.setText("Confirm");
            cancelButton.setText("Cancel");
            textPanel.add(confirmButton);
            textPanel.add(cancelButton);

            //functionality for confirm button

            confirmButton.addActionListener(f -> {
                String newMonth = monthBox.getSelectedItem().toString();
                String newDay = dayBox.getSelectedItem().toString();
                String newBirthday = newMonth + "/" + newDay;
                String insertData = "UPDATE player SET Birthday = ? WHERE name = ?";
                System.out.println("Birthday updated to: " + newBirthday);

                try (var conn =  DriverManager.getConnection(DB_URL)){ // creates a conn object that connects to the database
                PreparedStatement pstmt = conn.prepareStatement(insertData); // creates a statement that will be able to query the database
                System.out.println("Connected to SQLite Database");
                pstmt.setString(1, newBirthday);
                pstmt.setString(2, user_ID);


                System.out.println("Birthday updated!");
                pstmt.executeUpdate();
                birthday.setText(newBirthday);
                } catch(SQLException l){
                    System.out.println("failed to insert");
                textWindow.dispose();
                }});
            // functionality for cancel button
            cancelButton.addActionListener(d ->{
                textWindow.dispose();
            });
            } // end of confirm button
        );


        JButton changeBioButton = new JButton("Change Bio");
        settings.add(changeBioButton);
        changeBioButton.addActionListener( e -> { // creating frame and adding panel for change Bio
            System.out.println("Change bio button clicked");
            JFrame textWindow = new JFrame("Enter new bio");
            JPanel textPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            textWindow.add(textPanel);
            textWindow.setSize(600,300);
            textWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            textWindow.setVisible(true);
            JTextField textBox = new JTextField();
            textPanel.add(textBox);

            // add confirm / cancel buttons
            JButton confirmButton = new JButton();
            JButton cancelButton = new JButton();
            confirmButton.setText("Confirm");
            cancelButton.setText("Cancel");
            textPanel.add(confirmButton);
            textPanel.add(cancelButton);

            //functionality for confirm button

            confirmButton.addActionListener(f -> {
                String newBio = textBox.getText();
                if (newBio.length() >100){
                    JOptionPane.showMessageDialog(frame,
                    "Bio must be less than 100 characters. Currently bio is " + newBio.length()+" characters.");
                    //stops the registation if one of the fields are empty
                return;

                }else{
                String insertData = "UPDATE player SET Bio = ? WHERE name = ?";
                System.out.println("username updated to: " + newBio);

                try (var conn =  DriverManager.getConnection(DB_URL)){ // creates a conn object that connects to the database
                PreparedStatement pstmt = conn.prepareStatement(insertData); // creates a statement that will be able to query the database
                System.out.println("Connected to SQLite Database");
                pstmt.setString(1, newBio);
                pstmt.setString(2, user_ID);


                System.out.println("username updated!");
                pstmt.executeUpdate();
                bio.setText(newBio);
                } catch(SQLException l){
                    System.out.println("failed to insert");
                textWindow.dispose();
                }}});
            // functionality for cancel button
            cancelButton.addActionListener(d ->{
                textWindow.dispose();
            });
            } // end of confirm button
        );
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(k ->{
            login.createWindow();
            frame.dispose();
        });

        settings.add(logoutButton);


        toolbar.setPreferredSize(new Dimension(700,300));
        frame.add(toolbar, BorderLayout.EAST); // adds the toolbar to the main frame


    }



    // SOCIAL MEDIA PROFILE INSTEAD OF CHESS



}


