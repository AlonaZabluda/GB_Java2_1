package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button sendButton;

    @FXML
    private Label chatLabel;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField messageField;

    private String empty = "";


    public void clickButtonToSend(ActionEvent actionEvent) {
        if (!messageField.getText().equals(empty))
            textArea.appendText(messageField.getText() + "\n");
        messageField.clear();
    }

    public void pressEnterToSend(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && !messageField.getText().equals(empty)) {
            textArea.appendText(messageField.getText() + "\n");
            messageField.clear();
        }
    }

}
