package lesson6;

import java.io.*;
import java.net.Socket;

public class Client {
    final static String SERVER_ADRESS = "localhost";
    final static int SERVER_PORT = 8118;
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private static BufferedReader reader = null;


    public static void openConnection() {
        try {
            socket = new Socket(SERVER_ADRESS, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connection established");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String clientMsg = in.readUTF();
                            System.out.println("Client message:" + clientMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            while (!socket.isClosed()) {
                String serverMsg = reader.readLine();
                System.out.println("Server message: ");
                out.writeUTF(serverMsg);
                out.flush();
                if (serverMsg.equals("end")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        openConnection();

    }
}

