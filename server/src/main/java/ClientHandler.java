import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private MyServer server;
    private Socket socket;
    private ObjectInputStream inObj;
    private ObjectOutputStream outObj;

    private ClientProfile clientProfile;

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public ClientHandler(MyServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.inObj = new ObjectInputStream(socket.getInputStream());
            this.outObj = new ObjectOutputStream(socket.getOutputStream());
            this.clientProfile = null;
            new Thread(() -> {
                server.getAuthService().auth();
            });
        }
    }
}
