package lesson8_ChatFX;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final int TIMEOUT = 120000;
    private String name;


    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    timerForAuth();
                    readMsg();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Problems creating client handler");
        }
    }

    public void authentication() throws IOException {
        while (true) {
            String s = in.readUTF();
            if (s.startsWith("/auth")) {
                String[] parts = s.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " in a chat");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Account is already in use");
                    }
                } else {
                    sendMsg("Invalid username/password");
                }
            }
        }
    }


    public void timerForAuth() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    authentication();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        long endTimeMillis = System.currentTimeMillis() + TIMEOUT;
        while (thread.isAlive()) {
            if (System.currentTimeMillis() > endTimeMillis) {
                closeConnection();
                break;
            }
        }
    }


    public void readMsg() throws IOException {
        while (true) {
            String msgFromClient = in.readUTF();
            if (msgFromClient.startsWith("/")) {
                if (msgFromClient.equals("/end"))
                    break;
                if (msgFromClient.startsWith("/w ")) {
                    String[] parts = msgFromClient.split(" ", 3);
                    myServer.singleMsgToClient(ClientHandler.this, parts[1], parts[2]);
                }
            } else
                myServer.broadcastMsg(name + ": " + msgFromClient);
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " left the chat");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

