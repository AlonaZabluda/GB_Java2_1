package ChatJavaFX_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int SERVER_PORT = 8118;

    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {

        return authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Server is waiting for a connection");
                Socket socket = server.accept();
                System.out.println("Client connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Server error");
        } finally {
            if (authService != null) {
                authService.stop();
            }

        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler client : clients)
            if (client.getName().equals(nick)) {
                return true;
            }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler client : clients)
            client.sendMsg(msg);
    }

    public synchronized void unsubscribe(ClientHandler client) {

        clients.remove(client);
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);

    }

    public void singleAdd(ClientHandler fromClient, String toClient, String msg) {
        for (ClientHandler client : clients) {
            if(client.getName().equals(toClient)){
                client.sendMsg("from " + fromClient.getName() + ": " + msg);
                fromClient.sendMsg("to " + toClient + " you sent: " + msg);
                return;
            }
        }
        fromClient.sendMsg("Nick: " + toClient + " was not found in the chat.");
    }
}