import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private int PORT;
    private String IP_ADDR;
    private Socket socket;
    private ObjectInputStream inObj;
    private ObjectOutputStream outObj;

    public Client () {
        this.socket = new Socket("localhost", 8189);
        this.inObj = new ObjectInputStream(socket.getInputStream());
        this.outObj = new ObjectOutputStream(socket.getOutputStream());

    }



}