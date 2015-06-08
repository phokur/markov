
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

public class GUI extends Board {

    // An array of JLabels to hold images of pieces
    private JLabel[] pieceList = new JLabel[64];
    // Checks is player's turn
    protected boolean isTurn = false;
    // Checks if player has moved
    protected boolean isReady = false;
    // Holds coordinates for piece movements
    protected int xFrom;
    protected int yFrom;
    protected int xTo;
    protected int yTo;
    // Players color, black by default
    String color = "Black";

    public GUI() {
        init();
    }

    /*
     * Initiates the board GUI
     */
    private void init() {
        // Creates a new window to display in
        JFrame frame = new JFrame("ChesSilly");
        frame.setSize(920, 938);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creates a layered pane to enable overlapping
        JLayeredPane pane = new JLayeredPane();

        // Waits for mouse button presses and releases
        pane.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                mouseButtonPressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mouseButtonReleased(evt);
            }
        });

        // Creates the background image or board
        JLabel backBoard = new JLabel();
        backBoard.setBounds(0, 0, 912, 912);
        backBoard.setIcon(new ImageIcon("board.png"));
        // Added to first pane so it stays behind pieces
        pane.add(backBoard, new Integer(1));

        // Adds all piece images to all 100x100 squares on the chessboard
        int i = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] != null) {
                    pieceList[i] = new JLabel();
                    // Adjustments for border taken into account
                    pieceList[i].setBounds(x * 100 + 60, y * 100 + 60, 100, 100);
                    pieceList[i].setIcon(new ImageIcon(board[x][y].id.substring(2) + ".png"));
                    pane.add(pieceList[i], new Integer(2));
                    i++;
                }
            }
        }

        // Necessary stuff to add the layered pane to the frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(pane));
        layout.setVerticalGroup(layout.createParallelGroup().addComponent(pane));

        // Makes the window visible
        frame.setVisible(true);
    }

    /*
     * When the mouse button is pressed...
     */
    public void mouseButtonPressed(java.awt.event.MouseEvent evt) {
        if (isTurn) {
            // Sets the coordinates from mouse location when button is pressed
            xFrom = evt.getX();
            yFrom = evt.getY();
            // Calculations to count clicks on borders to closest piece
            if (xFrom < 60) {
                xFrom = 0;
            } else if (xFrom <= 860) {
                xFrom = xFrom - 60;
            } else {
                xFrom = 700;
            }
            if (yFrom < 60) {
                yFrom = 0;
            } else if (yFrom <= 860) {
                yFrom = yFrom - 60;
            } else {
                yFrom = 700;
            }
            // Divides final coordinates by 100 to get square number
            xFrom = xFrom / 100;
            yFrom = yFrom / 100;
        }
    }

    /*
     * When the mouse button is released...
     */
    public void mouseButtonReleased(java.awt.event.MouseEvent evt) {
        if (isTurn) {
            // Sets coordinates from mouse location when button is released
            xTo = evt.getX();
            yTo = evt.getY();
            // Calculations to count clicks on borders to closest piece
            if (xTo < 60) {
                xTo = 0;
            } else if (xTo <= 860) {
                xTo = xTo - 60;
            } else {
                xTo = 700;
            }
            if (yTo < 60) {
                yTo = 0;
            } else if (yTo <= 860) {
                yTo = yTo - 60;
            } else {
                yTo = 700;
            }
            // Divides final coordinates by 100 to get square number
            xTo = xTo / 100;
            yTo = yTo / 100;
            // Only allows move if piece color is player's color
            if (color.substring(0, 1).equals(board[xFrom][yFrom].id.substring(2, 3))) {
                boolean islegal = isLegal(xFrom, yFrom, xTo, yTo);
                System.out.println(islegal);
                if (isLegal(xFrom, yFrom, xTo, yTo)) {
                    // Swaps the two pieces, the ones from mousePressed and mouseReleased
                    movePiece(xFrom, yFrom, xTo, yTo);
                    // Updates the board GUI
                    update();
                    // Sets to true because a move has been made
                    isReady = true;
                }
            }
        }
    }

    /*
     * Changes all of the pictures in the JLabel array to match the board array
     */
    public void update() {
        int i = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != null) {
                    pieceList[i].setIcon(new ImageIcon(board[x][y].id.substring(2) + ".png"));
                    i++;
                }
            }
        }
    }
}
