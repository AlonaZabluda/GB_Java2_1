package lesson6;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int SERVER_PORT = 8118;
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private static BufferedReader reader = null;

    public Server(){
        try {
            server = new ServerSocket(SERVER_PORT);
            System.out.println("Server is working");
            socket = server.accept();
            System.out.println("Client is connected");
            in =  new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String serverMsg = in.readUTF();
                            System.out.println("Server message: " + serverMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            while (!socket.isClosed()) {
                String clientMsg = reader.readLine();
                System.out.println("Client message: ");
                out.writeUTF(clientMsg);
                out.flush();
                if (clientMsg.equals("end")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
                System.out.println("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}
