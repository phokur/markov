
import java.io.Serializable;

/*
 * Message is used to communicate 
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 727959509258087056L;

    public enum MessageType {

        CLIENTCONNECT, CLIENTDISCONNECT, STARTPLAY, MESSAGE, MOVE, WIN
    }
    public MessageType messageType;
    public int rowFrom, colFrom, rowTo, colTo;
    public String text;

    public Message() {
    }

    // Move message setup
    public Message(int rFrom, int cFrom, int rTo, int cTo) {
        rowFrom = rFrom;
        colFrom = cFrom;
        rowTo = rTo;
        colTo = cTo;
        messageType = MessageType.MOVE;
    }

    // Chat/Other Message type setup
    public Message(MessageType type, String chat) {
        text = chat;
        messageType = type;
    }

    public Message(MessageType type) {
        messageType = type;
    }

    // Get type of message, can also be access publicly
    public MessageType getType() {
        return messageType;
    }

    // Returns the message
    public String getText() {
        return text;
    }
}
