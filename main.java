import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Main {

    public static void main(String[] args) {
        
    }


    public static void initializeDefaultProfile(String user_ID){ // function to be run at the beginning of main that creates the empty
        //profile fields to be replaced later
        //

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


        // user info fields
        JLabel bio = new JLabel("User bio here");
        JLabel username = new JLabel("Username here");
        JLabel birthday = new JLabel("Birthday here");
        // sets a size for the panel so it does not push the right side panel off of the screen
        descriptionPanel.setPreferredSize(new Dimension(600,100));


        //add labels to panel
        descriptionPanel.add(bio);
        descriptionPanel.add(username);
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

        // buttons for the setting tab 
        JButton profileButton = new JButton("Change Profile Picture");
        settings.add(profileButton);
        JButton changeUsernameButton = new JButton("Change username");
        settings.add(changeUsernameButton);
        JButton changeBioButton = new JButton("Change Bio");
        settings.add(changeBioButton);
        JButton logoutButton = new JButton("Logout");
        settings.add(logoutButton);


        toolbar.setPreferredSize(new Dimension(700,300));
        frame.add(toolbar, BorderLayout.EAST); // adds the toolbar to the main frame


        // LOAD SQL
        // bio.setTEXT ( SQL QUERY)
        // name. setText ( SQL QUERY)
        // ````
    }



    // SOCIAL MEDIA PROFILE INSTEAD OF CHESS



}


