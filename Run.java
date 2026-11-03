import javax.swing.SwingUtilities;

public class Run {    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::createWindow);
        Test.run();
    }

    // public static void main(String[] args) {
    //             SwingUtilities.invokeLater(Login::createWindow);
    //     SwingUtilities.invokeLater(() -> createWindow());
    // }

}