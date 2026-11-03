
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;

public class test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createWindow());
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Placeholder Name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Navigation bar
        JPanel navBar = new JPanel();

        JButton homeButton = new JButton("Home");
        JButton profileButton = new JButton("Profile");
        JButton friendsButton = new JButton("Friends");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Logout");

        navBar.add(homeButton);
        navBar.add(profileButton);
        navBar.add(friendsButton);
        navBar.add(settingsButton);
        navBar.add(logoutButton);

        frame.add(navBar, BorderLayout.NORTH);

        // Main content area
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Empty side panels keep the posts feed centered
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(180, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(180, 0));

        // Posts feed
        JPanel postsPage = new JPanel(new BorderLayout());

        JLabel postsTitle = new JLabel("Feed", JLabel.CENTER);
        postsTitle.setBorder(
            BorderFactory.createEmptyBorder(15, 5, 15, 5)
        );

        postsPage.add(postsTitle, BorderLayout.NORTH);

        // Create-post area
        JPanel createPostPanel = new JPanel(new BorderLayout(5, 5));
        createPostPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JTextArea postInput = new JTextArea(3, 25);
        postInput.setLineWrap(true);
        postInput.setWrapStyleWord(true);

        JScrollPane inputScroll = new JScrollPane(postInput);
        inputScroll.setBorder(
            BorderFactory.createTitledBorder("What's on your mind?")
        );

        JButton createPostButton = new JButton("Create Post");

        createPostPanel.add(inputScroll, BorderLayout.CENTER);
        createPostPanel.add(createPostButton, BorderLayout.SOUTH);

        postsPage.add(createPostPanel, BorderLayout.SOUTH);

        // Placeholder posts
        JPanel postsFeed = new JPanel();
        postsFeed.setLayout(
            new BoxLayout(postsFeed, BoxLayout.Y_AXIS)
        );
        postsFeed.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        postsFeed.add(createPostCard(
            "Sample Post 1 here"
        ));
        postsFeed.add(new JLabel(" "));

        postsFeed.add(createPostCard(
            "Sample Post 2 here"
        ));
        postsFeed.add(new JLabel(" "));

        postsFeed.add(createPostCard(
            "Sample Post 3 here"
        ));

        JScrollPane postsScroll = new JScrollPane(postsFeed);
        postsScroll.setBorder(BorderFactory.createEmptyBorder());

        postsPage.add(postsScroll, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(postsPage, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);

        // All buttons are visual only.
        // No action listeners are attached.

        frame.setVisible(true);
    }

    // Creates a simple post card for the feed
    private static JPanel createPostCard(String content) {
        JPanel postCard = new JPanel(new BorderLayout());

        postCard.setBorder(
            BorderFactory.createTitledBorder("Post")
        );
        postCard.setPreferredSize(new Dimension(450, 90));
        postCard.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, 100
        ));

        JLabel postText = new JLabel(content);
        postText.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        postCard.add(postText, BorderLayout.CENTER);

        return postCard;
    }
}
