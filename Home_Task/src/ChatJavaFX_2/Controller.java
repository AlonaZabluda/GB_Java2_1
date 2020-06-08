package ChatJavaFX_2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private AnchorPane registration;

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;

    @FXML
    private AnchorPane chat;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField messageField;


    final static String SERVER_ADDRESS = "localhost";
    final static int SERVER_PORT = 8118;
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    boolean isAuthorized;


    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            registration.setVisible(true);
            registration.setManaged(true);
            chat.setVisible(false);
            chat.setManaged(false);
        } else {
            registration.setVisible(false);
            registration.setManaged(false);
            chat.setVisible(true);
            chat.setManaged(true);
        }
    }

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.startsWith("/authok")) {
                                setAuthorized(true);
                                break;
                            } else {
                                textArea.appendText(strFromServer + "\n");
                            }
                        }
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equals("/end")) break;
                            textArea.appendText(strFromServer + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Controller.this.setAuthorized(false);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendMessage() {
        try {
            out.writeUTF(messageField.getText());
            messageField.clear();
            messageField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toAuth() {
        try {
            out.writeUTF("/auth " + login.getText() + " " + password.getText());
            login.clear();
            password.clear();
            setAuthorized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
