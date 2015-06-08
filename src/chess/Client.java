
import java.io.*;
import java.net.*;

public class Client extends Thread {

    private static Socket socket = null;
    protected static Message message = null;
    protected static ObjectOutputStream out = null;
    private static ObjectInputStream in = null;
    private static boolean gameDone = false;
    private static GUI board = new GUI();
    private static PlayTimer timer = new PlayTimer();
    protected static String serverIP = null;
    protected static String thisPlayer = null;
    private static String opponent = null;

    public Client(String server, String player) {
        thisPlayer = player;
        serverIP = server;
        connectToServer();
        this.start();
    }

    /*
     * Cases for what to do with messages
     */
    public void run() {
        try {
            // Messages this player's name
            message = new Message(Message.MessageType.CLIENTCONNECT, thisPlayer);
            out.writeObject(message);
            // Receives other player's name
            message = (Message) in.readObject();
            opponent = message.text;
            // Greets player
            timer.greet(thisPlayer);
            while (gameDone != true) {
                message = (Message) in.readObject();
                switch (message.messageType) {
                    case CLIENTCONNECT: // ADD USERNAME AS ENEMY PLAYER
                        break;
                    case CLIENTDISCONNECT: // GRACEFULLY EXIT
                        System.exit(0);
                        break;
                    case STARTPLAY:
                        // MAKE THIS PLAYER WHITE
                        // RECEIVING THIS FIRST MESSAGE MAKES
                        // THEM GO FIRST ANYWAY
                        board.color = "White";
                        board.iswhite = true;
                        board.isTurn = true;
                        break;
                    case MOVE: // HANDLES A MOVE MESSAGE
                        // Updates player's board to match other player's move
                        board.movePiece(message.rowFrom, message.colFrom, message.rowTo, message.colTo);
                        // Updates the GUI to reflect the new piece posititons
                        board.update();
                        // Unlocks board for player's turn
                        board.isTurn = true;
                        // Stops other player's timer
                        timer.stopTimer();
                        // Starts player's timer
                        timer.startTimer(thisPlayer);
                        break;
                    case WIN:  // Other player has WON the game
                    // Handle like a move except they won
                    // the game and game is done.
                    default:
                        System.out.println("Message was untyped and inappropiate!");
                }
                // Loop to prevent move until player's turn
                while (board.isReady == false) {
                    // Loops forever
                }
                // Sends this player's last move
                message = new Message(board.xFrom, board.yFrom, board.xTo, board.yTo);
                out.writeObject(message);
                // Locks board
                board.isTurn = false;
                board.isReady = false;
                // Stops player's timer
                timer.stopTimer();
                // Starts other player's timer
                timer.startTimer(opponent);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Connect to the server
     */
    private static void connectToServer() {
        try {
            socket = new Socket(serverIP, 9001);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to Server");
        } catch (Exception e) {
            System.out.println("System failed to open connection to server.");
        }
    }
}
