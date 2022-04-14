package server;

import network.ClientProfile;
import network.Message;
import network.TypeMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MyServer {

    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;
    private ClientProfile adminProfile;

    public ClientProfile getAdminProfile() {
        return adminProfile;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            authService = new DataBase();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void broadcastMsg(Message<String> message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMsg(message);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler, boolean sub) {
        if (sub) clients.add(clientHandler);
        Message<String> messageServer = new Message<String>(clientHandler.getClientProfile().getName() + " вошел в чат",
                adminProfile, TypeMessage.VERBAL_MESSAGE);
        System.out.println(adminProfile.getName() + ": " + clientHandler.getClientProfile().getName() + " вошел в чат");
        broadcastMsg(messageServer);
    }

}
