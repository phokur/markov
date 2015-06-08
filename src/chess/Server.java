
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {

    private ServerSocket serversocket = null;
    private ClientConnection waitingclient = null;
    private ArrayList<GamePair> serverGames = new ArrayList<GamePair>();

    /*
     * Creates the Server
     */
    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            System.out.println("Server failed to launch.");
        }
    }

    /*
     * Opens the server socket and starts the server thread
     */
    public Server() {
        try {
            serversocket = new ServerSocket(9001);
            System.out.println("Server listening at " + (InetAddress.getLocalHost()).getHostAddress() + ":9001");
            this.start();
        } catch (Exception e) {
            System.out.println("Socket already in use.");
        }
    }

    /*
     * Continually matches pairs of clients as they connect to the server
     *
     * @see java.lang.Thread#run()
     */
    public void run() {
        System.out.println("Server Waiting!");
        for (;;) {
            try {
                Socket tempsocket = serversocket.accept();
                if (waitingclient == null) {
                    waitingclient = new ClientConnection(tempsocket);
                    System.out.println("A Client is Waiting");
                } else {
                    System.out.println("Making Game Pair");
                    GamePair temp = new GamePair(waitingclient,
                            new ClientConnection(tempsocket));
                    serverGames.add(temp);
                    System.out.println("Please wait while we set up the game.");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("GamePair has been made");
                    temp.startGame();
                    waitingclient = null;
                }
            } catch (Exception e) {
                System.out.println("Error in accepting client");
            }
        }
    }
}

class ClientConnection extends Thread {

    private Socket client = null;
    private ObjectInputStream inFromClient = null;
    private ObjectOutputStream outToClient = null;
    private boolean goesFirst = false;
    private boolean gameDone = false;

    public ClientConnection(Socket clientsocket) {
        client = clientsocket;
        System.out.println("Setting up Streams");
        try {
            if (client.isClosed()) {
                System.out.println("Client is closed?");
            }
            outToClient = new ObjectOutputStream(client.getOutputStream());
            inFromClient = new ObjectInputStream(client.getInputStream());
            System.out.println("Set up the client Streams!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Asks this client for a message
    public Message getMessage() {
        try {
            return (Message) inFromClient.readObject();
        } catch (Exception e) {
            System.out.println("Failed to get Message.");
        }
        return new Message();
    }

    // Send Message to this client
    public void sendMessage(Message send) {
        try {
            outToClient.writeObject(send);
        } catch (Exception e) {
            System.out.println("Failed to send message to client");
        }
    }

    // Tells a client to go first
    public void setFirstPlayer() {
        goesFirst = true;
        Message gofirstmessage = new Message(Message.MessageType.STARTPLAY);
        try {
            outToClient.writeObject(gofirstmessage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class GamePair extends Thread {

    private ClientConnection player;
    private ClientConnection opponent;
    private Message currentComm = null;
    private boolean gameDone = false;

    // Sets up game pair properly
    public GamePair(ClientConnection one, ClientConnection two) {
        player = one;
        opponent = two;
    }

    // Sets up first player and starts the game
    public void startGame() {
        System.out.println("Starting the Game");
        currentComm = player.getMessage();
        opponent.sendMessage(currentComm);
        currentComm = opponent.getMessage();
        player.sendMessage(currentComm);
        player.setFirstPlayer();
        this.start();
    }

    /*
     * Sends messages back and forth, need to add code that will stop the game
     * gracefully if player disconnects or WIN message is sent
     *
     * Right now this infinetly loops
     *
     * @see java.lang.Thread#run()
     */
    public void run() {
        while (gameDone != true) {
            currentComm = player.getMessage();
            switch (currentComm.messageType) {
                case CLIENTDISCONNECT: // GRACEFULLY EXIT
                    System.exit(0);
                    break;
                case WIN: // Handle WIN message
                    break;
                default:
                // Do nothing, just forward message
            }
            opponent.sendMessage(currentComm);
            currentComm = opponent.getMessage();
            switch (currentComm.messageType) {
                case CLIENTDISCONNECT: // GRACEFULLY EXIT
                    System.exit(0);
                    break;
                case WIN: // Handle WIN message
                    break;
                default:
                // Do nothing, just forward message
            }
            player.sendMessage(currentComm);
        }
    }
}
