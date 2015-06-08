
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Main {

    // A new frame for the window
    private static JFrame frame = new JFrame("ChesSilly");
    // An usernameInput text field for username
    private static JTextField usernameInput = new JTextField();
    // An usernameInput text field for server ip address
    private static JTextField ipInput = new JTextField();

    public static void main(String[] args) {
        {
            // Sets up a new window to display in
            frame.setSize(920, 938);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // Creates a layered pane to enable overlapping
            JLayeredPane pane = new JLayeredPane();

            // Creates the background image or board
            JLabel backBoard = new JLabel();
            backBoard.setBounds(0, 0, 912, 912);
            backBoard.setIcon(new ImageIcon("title.png"));
            // Added to first pane so it stays behind pieces
            pane.add(backBoard, new Integer(1));

            // Main title
            JLabel chesSilly = new JLabel();
            chesSilly.setBounds(0, 75, 912, 175);
            chesSilly.setFont(new java.awt.Font("Segoe Print", 1, 120));
            chesSilly.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            chesSilly.setText("ChesSilly");
            pane.add(chesSilly, new Integer(2));

            // Tells player to enter username
            JLabel instructions = new JLabel();
            instructions.setBounds(0, 320, 912, 45);
            instructions.setFont(new java.awt.Font("Segoe Print", 1, 34));
            instructions.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            instructions.setText("Please enter your user name...");
            pane.add(instructions, new Integer(2));

            // Sets up the usernameInput text field
            usernameInput.setBounds(250, 380, 400, 50);
            usernameInput.setFont(new java.awt.Font("Segoe Print", 1, 36));
            usernameInput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            pane.add(usernameInput, new Integer(2));

            // Tells player to enter username
            JLabel instructions2 = new JLabel();
            instructions2.setBounds(0, 450, 912, 45);
            instructions2.setFont(new java.awt.Font("Segoe Print", 1, 34));
            instructions2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            instructions2.setText("...and a Server IP Address");
            pane.add(instructions2, new Integer(2));

            // Sets up the usernameInput text field
            ipInput.setBounds(250, 500, 400, 50);
            ipInput.setFont(new java.awt.Font("Segoe Print", 1, 36));
            ipInput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            pane.add(ipInput, new Integer(2));

            // A JButton to create the game
            JButton button = new JButton();
            button.setBounds(300, 620, 300, 100);
            button.setFont(new java.awt.Font("Segoe Print", 1, 36));
            button.setText("Join Game");
            pane.add(button, new Integer(2));

            // Listens for the button to be pressed
            button.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    buttonClicked(evt);
                }
            });

            // Gives the authors credit
            JLabel authors = new JLabel();
            authors.setBounds(0, 780, 912, 40);
            authors.setFont(new java.awt.Font("Segoe Print", 1, 26));
            authors.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            authors.setText("Created By: Justin Raines and Scott Zupancic");
            pane.add(authors, new Integer(2));

            // Gives the graphics artist credit
            JLabel identity = new JLabel();
            identity.setBounds(0, 810, 912, 40);
            identity.setFont(new java.awt.Font("Segoe Print", 1, 26));
            identity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            identity.setText("Graphics By: Sheri Dean");
            pane.add(identity, new Integer(2));

            // Necessary stuff to add the layered pane to the frame
            GroupLayout layout = new GroupLayout(frame.getContentPane());
            frame.getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup().addComponent(pane));
            layout.setVerticalGroup(layout.createParallelGroup().addComponent(pane));

            // Makes the window visible
            frame.setVisible(true);
        }
    }

    /*
     * If the mousebutton is clicked...
     * */
    private static void buttonClicked(java.awt.event.MouseEvent evt) {

        // If the button is clicked and the text box isn't blank or contains the error message...
        if (usernameInput.getText().isEmpty() || usernameInput.getText().equalsIgnoreCase("Enter a user name")) {
            // Sends an error message
            usernameInput.setText("Enter a user name");
        } else if (ipInput.getText().isEmpty() || ipInput.getText().equalsIgnoreCase("Enter a Server IP Address")) {
            ipInput.setText("Enter a Server IP Address");
        } else {
            // Starts a new game
            Client newGame = new Client(ipInput.getText(), usernameInput.getText());
            // Sets user name and server ip address
            // Kills the welcome screen
            frame.dispose();
        }
    }
}
