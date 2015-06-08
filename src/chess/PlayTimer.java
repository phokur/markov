
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class PlayTimer implements ActionListener {

    // New timer
    private Timer tymar = new Timer(1, this);
    // JLabel to hold the current player's name
    private JLabel identity = new JLabel();
    // JLabel to display the timer
    private JLabel time = new JLabel();
    // JLabel to display the total time
    private JLabel totalTime = new JLabel();
    // Counters to keep track of instance to use for milliseconds
    private int tick = 0;
    private int tock = 0;

    public PlayTimer() {
        // New window to hold timer
        JFrame frame = new JFrame("Timer");
        frame.setSize(157, 125);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Layered pane to hold JLabels over background
        JLayeredPane pane = new JLayeredPane();

        // JLabel to represent the background
        JLabel background = new JLabel();
        background.setBounds(0, 0, 150, 100);
        background.setIcon(new ImageIcon("Timer.png"));
        pane.add(background, new Integer(1));

        // Sets up the identity JLabel
        identity.setBounds(0, 0, 150, 45);
        identity.setFont(new java.awt.Font("Segoe Print", 1, 32));
        identity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pane.add(identity, new Integer(2));

        // Sets up the time JLabel
        time.setBounds(0, 35, 150, 40);
        time.setFont(new java.awt.Font("Segoe Print", 1, 24));
        time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pane.add(time, new Integer(2));

        // Sets up the total time JLabel
        totalTime.setBounds(0, 70, 150, 30);
        totalTime.setFont(new java.awt.Font("Segoe Print", 1, 18));
        totalTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pane.add(totalTime, new Integer(2));

        // Adds the layered pane to the frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(pane));
        layout.setVerticalGroup(layout.createParallelGroup().addComponent(pane));

        // Makes the timer visible
        frame.setVisible(true);
    }

    /*
     * Calculates then sends the time to JLabel time
     */
    public void actionPerformed(ActionEvent e) {
        time.setText(formatTime(tick++));
        totalTime.setText(formatTime(tock++));
    }

    /*
     * Formats the time from milliseconds to MM:SS:MS
     */
    private String formatTime(int ms) {
        // Holds the formatted time
        String formattedTime = null;
        // Holds seconds and minutes for calculations
        int sec = 0;
        int min = 0;

        // Calculates the minutes and seconds off of milliseconds
        min = ms / 60000;
        ms = ms - (min * 60000);
        sec = ms / 1000;
        ms = ms - (sec * 1000);

        // Adds milliseconds to formattedTime
        if (ms == 0) {
            formattedTime = ":000";
        } else if (ms < 10) {
            formattedTime = ":00" + ms;
        } else if (ms < 100) {
            formattedTime = ":0" + ms;
        } else {
            formattedTime = ":" + ms;
        }
        // Adds seconds to formattedTime
        if (sec == 0) {
            formattedTime = ":00" + formattedTime;
        } else if (sec < 10) {
            formattedTime = ":0" + sec + formattedTime;
        } else {
            formattedTime = ":" + sec + formattedTime;
        }
        // Adds minutes to formattedTime
        if (min == 0) {
            formattedTime = "00" + formattedTime;
        } else if (min < 60) {
            formattedTime = "0" + min + formattedTime;
        } else {
            formattedTime = "" + min + formattedTime;
        }
        return formattedTime;
    }

    /*
     * Greeting for initial launch
     */
    public void greet(String id) {
        identity.setText("Welcome");
        time.setText(id);
        totalTime.setText("to ChesSilly!");
    }

    /*
     * Starts the Timer
     */
    public void startTimer(String id) {
        // Displays who's turn it is
        identity.setText(id);
        // Resets the play ticker
        tick = 0;
        // Restarts the timer
        tymar.restart();
    }

    /*
     * Stops the Timer
     */
    public void stopTimer() {
        tymar.stop();
    }
}
