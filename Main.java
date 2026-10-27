// import the ui parts, including necessary layouts
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

//connects the databases, posts and user data, the variabls hold the profile info retrieved from the database
public class Main {
    private static final String DB_URL = "jdbc:sqlite:userdata.db";
    private static final String DB_PostURL = "jdbc:sqlite:posts.db";
    public static String retrievedBio;
    public static String retrievedBirthday;
    public static String retrievedUsername;

    // main method, this starts by opening the UI window
    public static void main(String[] args) {
        login.createWindow();
    }

    // declares the method that can be called from other classes, accepts user id as perameter, 
    //creates a string containg the SQL quesry we need from the db
    // selects the username from palyer where the name matches the users input
    public static void initializeDefaultProfile(String user_ID){ // function to be run at the beginning of main that creates the empty
        

        System.out.println("Loading Query:");
        String sqlQuery = "SELECT Username"
                        + " FROM player"
                        + " WHERE name = '" + user_ID + "'";
        System.out.println(sqlQuery);

        // opens a connection to the database and executes the SQL
        // creates the SQL statement object, stores its results in r 
        //reads the username column and saves it in retrieved username
        try (var conn =  DriverManager.getConnection(DB_URL)){ 
            var stmt = conn.createStatement(); 
            var rs = stmt.executeQuery(sqlQuery); 
            System.out.println("Connected to SQLite Database");

            // gets the username from the query result
            // stores the value in the variable so it can be used elsewhere
            retrievedUsername = rs.getString("Username"); 
            System.out.println(retrievedUsername);

            // gets the bio from the query result
            //replaces the previous query with one that selects teh bio
            //executes the new query and store it in the bio
            sqlQuery = "SELECT Bio FROM player WHERE name = '" + user_ID + "'";
            stmt.executeQuery(sqlQuery);
            retrievedBio = rs.getString("Bio");
            System.out.println(retrievedBio);

            // gets the birthday from the query result
            //replaces the preivous quesry with on that selects birthday
            //executes the new one and stores it in birthday
            sqlQuery = "SELECT Birthday FROM player WHERE name = '" + user_ID + "'";
            stmt.executeQuery(sqlQuery);
            retrievedBirthday = rs.getString("Birthday");
            System.out.println(retrievedBirthday);

            // catches SQL related exceptions that are thrown inside the try block
            // e.getmessage provdies the description of the error, and its printed to teh console after
        } catch (SQLException e){ System.out.println(e.getMessage());  
        }

        //creates the application window called my profile will change it to a new name in the future
        //makes it so that the app terminates when the user closes the window
        //sets the initial size of the window, 900x650
        //centers the screen to teh window
        //divides the window into five different regions, N,S,E,W and Center
        JFrame frame = new JFrame("My Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Nav bar, creates a container to group the related components
        // arranges the components horrrizontally
        //each JButton creates a clickable button with display text
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton homeButton = new JButton("Home");
        JButton profileNavButton = new JButton("Profile");
        JButton friendsNavButton = new JButton("Friends");
        JButton settingsNavButton = new JButton("Settings");
        //These add all the buttons to the insde nav panel, adding navbar to the top of the window too
        navBar.add(homeButton);
        navBar.add(profileNavButton);
        navBar.add(friendsNavButton);
        navBar.add(settingsNavButton);
        //This line specifically makes it appear on the top but again can be moved around if we change our mind 
        frame.add(navBar, BorderLayout.NORTH);

        // mainpanel uses borderlayout to organize the left sidebar, center and the right sidebar
        // This also specifies a new dimentions with a width of 180 pixels, no specific height is needed
        // side panels are empty right now leaving room for growth
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(180, 0));
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(180, 0));

        // The profile section is on the left
        //adds vertical spacing between the regions
        // jlabel displays the text and the nit gets centered horizontally
        //the prefered size creates the dimensitons of the profile picture area
        //the picture placeholder gets centered north above the profile details
        JPanel profileContent = new JPanel(new BorderLayout(5, 5));
        JPanel profilePanel = new JPanel(new BorderLayout());
        JLabel profileText = new JLabel("Profile picture will go here!", JLabel.CENTER);
        profilePanel.setPreferredSize(new Dimension(160, 180));
        profilePanel.add(profileText, BorderLayout.CENTER);
        profileContent.add(profilePanel, BorderLayout.NORTH);

        //creates a grid with one column and as many rows that are needed
        JPanel descriptionPanel = new JPanel(new GridLayout(0, 1));
        JLabel bio = new JLabel();
        JLabel username = new JLabel();
        JLabel birthday = new JLabel();
        //these are the default values of bio birthday and username, can be changed by the user but is displayed when there are no db value found

        bio.setText("No bio set yet!");
        birthday.setText("No birthday set yet!");
        username.setText("No Username set yet!");

        //checks if the variable isnt null, then if it contains a value replace the default text
        if (retrievedBio != null) bio.setText(retrievedBio);
        if (retrievedBirthday != null) birthday.setText(retrievedBirthday);
        if (retrievedUsername != null) username.setText(retrievedUsername);

        //Stacks the labels in the order or username, bio, birthday
        // adds 8 pixels of empty padding around the description panel
        //places the profile content at the top of the left sidebar
        descriptionPanel.add(username);
        descriptionPanel.add(bio);
        descriptionPanel.add(birthday);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        profileContent.add(descriptionPanel, BorderLayout.CENTER);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(profileContent, BorderLayout.NORTH);

        //Lets one panel contin more than one panel but only displays one at a time 
        CardLayout pageLayout = new CardLayout();
        JPanel pagePanel = new JPanel(pageLayout);

        // Feed title sits at the top with teh padding around it 
        JPanel postsPage = new JPanel(new BorderLayout());
        JLabel postsTitle = new JLabel("Feed", JLabel.CENTER);
        postsTitle.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        postsPage.add(postsTitle, BorderLayout.NORTH);

        //stacks the components vertically, y_axis
        // the empty border adds padding around the feed
        JPanel posts = new JPanel();
        posts.setLayout(new BoxLayout(posts, BoxLayout.Y_AXIS));
        posts.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creates a post card, the empty spaces are used to space out the cards 
        posts.add(createPostCard("Sample Post 1 here"));
        posts.add(new JLabel(" "));
        posts.add(createPostCard("Sample Post 2 here"));
        posts.add(new JLabel(" "));
        posts.add(createPostCard("Sample Post 3 here"));
        posts.add(new JLabel(" "));

        // provides scrolling when the feed becomes taller than the visible space
        //keeps it in the center of the page 
        JScrollPane postsScroll = new JScrollPane(posts);
        postsScroll.setBorder(BorderFactory.createEmptyBorder());
        postsPage.add(postsScroll, BorderLayout.CENTER);

    
        //create a text area with 3 rows and 25 columns as preffered demensions
        // warps long text on new lines
        // prefers to wrap at word boudaries rather than split words 
        JPanel createPostPanel = new JPanel(new BorderLayout(5, 5));
        createPostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextArea postInput = new JTextArea(3, 25);
        postInput.setLineWrap(true);
        postInput.setWrapStyleWord(true);

        //places the text area insde a scroll pane
        // gives it a titled border called whats on you mind
        //centers it at the bottom of the feed
        JScrollPane inputScroll = new JScrollPane(postInput);
        inputScroll.setBorder(BorderFactory.createTitledBorder("What's on your mind?"));
        createPostPanel.add(inputScroll, BorderLayout.CENTER);
        postsPage.add(createPostPanel, BorderLayout.SOUTH);


        //creates the friends page as a placeholder
        //is on the center of the main one, to the right a it
        JPanel friends = new JPanel(new BorderLayout());
        friends.add(new JLabel("Friends go here!", JLabel.CENTER), BorderLayout.NORTH);
        JPanel settings = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //string names identify each page
        pagePanel.add(postsPage, "Posts");
        pagePanel.add(friends, "Friends");
        pagePanel.add(settings, "Settings");

        //everything gets positioned to the correct spots
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(pagePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        frame.add(mainPanel, BorderLayout.CENTER);


        // listeners reach or look out for a button click
        // e -> represents what action to perform
        // couple of them are placeholders
        homeButton.addActionListener(e -> pageLayout.show(pagePanel, "Posts"));
        profileNavButton.addActionListener(e -> pageLayout.show(pagePanel, "Posts"));
        friendsNavButton.addActionListener(e -> pageLayout.show(pagePanel, "Friends"));
        settingsNavButton.addActionListener(e -> pageLayout.show(pagePanel, "Settings"));
       
        //begins to load a post from SQLLite

        // intj=1 sets the post id to 1

            int j = 1;

            //this is the intended query
            String postSqlQuery = "SELECT PostText"
                                    + " FROM posts"
                                    + " WHERE PostID = '" + j + "'"; 
                                    
            System.out.println(postSqlQuery);            


            //opens the posts database and executes the query
            //retrieves the post text, creates a label containing it 
            //then adds that label to the feed panel
            try(var conn = DriverManager.getConnection(DB_PostURL)){
  
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(postSqlQuery);
                System.out.println("Connected to SQLite Database");
                String retrievedText = rs.getString("PostText"); 
                JLabel text = new JLabel("\n" + retrievedText);//Not making it on new line
                System.out.println(retrievedText);
                posts.add(text);

            //db error for debugging
            } catch (SQLException e1){
                System.out.println(e1.getMessage());
            }



        // buttons for the posts tab 
        JButton postsButton = new JButton("Create Post");
        createPostPanel.add(postsButton, BorderLayout.SOUTH);
        postsButton.addActionListener(e -> {
            System.out.println("Post button clicked");
            try(var conn = DriverManager.getConnection(DB_PostURL)){
                String checkSql =
                    "SELECT 1 FROM post WHERE name = ?";
                   
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

        frame.setVisible(true);



    }



    // Creates a post card matching new UI
    private static JPanel createPostCard(String content) {
        JPanel postCard = new JPanel(new BorderLayout());
        postCard.setBorder(BorderFactory.createTitledBorder("Post"));
        postCard.setPreferredSize(new Dimension(450, 90));
        postCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel postText = new JLabel(content);
        postText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postCard.add(postText, BorderLayout.CENTER);
        return postCard;
    }

    // SOCIAL MEDIA PROFILE INSTEAD OF CHESS

}


